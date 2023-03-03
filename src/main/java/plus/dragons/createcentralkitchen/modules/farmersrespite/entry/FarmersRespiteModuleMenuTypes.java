package plus.dragons.createcentralkitchen.modules.farmersrespite.entry;

import com.tterrag.registrate.builders.MenuBuilder;
import com.tterrag.registrate.util.entry.MenuEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.world.inventory.AbstractContainerMenu;
import plus.dragons.createcentralkitchen.CentralKitchen;
import plus.dragons.createcentralkitchen.modules.farmersrespite.content.logistics.item.guide.BrewingGuideMenu;
import plus.dragons.createcentralkitchen.modules.farmersrespite.content.logistics.item.guide.BrewingGuideScreen;

public class FarmersRespiteModuleMenuTypes {

    public static final MenuEntry<BrewingGuideMenu> BREWING_GUIDE =
            register("brewing_guide", BrewingGuideMenu::new, () -> BrewingGuideScreen::new);

    private static <C extends AbstractContainerMenu, S extends Screen & MenuAccess<C>> MenuEntry<C> register(
            String name, MenuBuilder.ForgeMenuFactory<C> factory, NonNullSupplier<MenuBuilder.ScreenFactory<C, S>> screenFactory) {
        return CentralKitchen.REGISTRATE
                .menu(name, factory, screenFactory)
                .register();
    }

    public static void register() {}
    
}
