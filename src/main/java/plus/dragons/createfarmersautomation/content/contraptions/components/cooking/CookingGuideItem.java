package plus.dragons.createfarmersautomation.content.contraptions.components.cooking;

import com.simibubi.create.content.contraptions.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.content.contraptions.processing.burner.BlazeBurnerTileEntity;
import com.simibubi.create.foundation.advancement.AdvancementBehaviour;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import plus.dragons.createfarmersautomation.entry.CfaBlocks;
import plus.dragons.createfarmersautomation.entry.CfaItems;

import java.util.ArrayList;
import java.util.List;

public class CookingGuideItem extends Item implements MenuProvider {
    // TODO Texture & Lang
    public CookingGuideItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext useOnContext) {
        var level = useOnContext.getLevel();
        var player = useOnContext.getPlayer();
        if (player == null)
            return InteractionResult.PASS;
        if (player.isShiftKeyDown()) {
            var itemStack = useOnContext.getItemInHand();
            if (itemStack.is(CfaItems.COOKING_GUIDE.get())) {
                var blockPos = useOnContext.getClickedPos();
                var blockState = level.getBlockState(blockPos);
                var blockEntity = level.getBlockEntity(blockPos);
                if (blockState.getBlock() instanceof BlazeBurnerBlock &&
                        blockEntity instanceof BlazeBurnerTileEntity)
                {
                    if (!level.isClientSide()) {
                        level.setBlockAndUpdate(blockPos, CfaBlocks.BLAZE_STOVE.getDefaultState()
                                .setValue(BlazeStoveBlock.FACING, level.getBlockState(blockPos).getValue(BlazeBurnerBlock.FACING)));
                        if(level.getBlockEntity(blockPos) instanceof BlazeStoveBlockEntity blazeStove){
                            var in = itemStack.copy();
                            in.setCount(1);
                            blazeStove.setCookingGuide(in);
                        }

                        // TODO ADVANCEMENT
                        AdvancementBehaviour.setPlacedBy(useOnContext.getLevel(), blockPos, player);
                        // CeiAdvancements.BLAZES_NEW_JOB.getTrigger().trigger((ServerPlayer) player);

                        if (!player.getAbilities().instabuild)
                            itemStack.shrink(1);
                    }
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public Component getDisplayName() {
        return getDescription();
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, @NotNull Inventory pPlayerInventory, @NotNull Player pPlayer) {
        // TODO
        return null;
    }

    public static List<ItemStack> getContent(ItemStack itemStack){
        var ret = new ArrayList<ItemStack>();
        var tag = itemStack.getOrCreateTag();
        for(int i=0;i<6;i++){
            if(tag.contains("Ingredient" + i)){
                ret.add(ItemStack.of(tag.getCompound("Ingredient" + i)));
            } else ret.add(ItemStack.EMPTY);
        }
        return ret;
    }

    public static void saveContent(List<ItemStack> content,ItemStack itemStack){
        var tag = itemStack.getOrCreateTag();
        for(int i=0;i<6;i++){
            tag.put("Ingredient"+i,content.get(i).serializeNBT());
        }
    }
}
