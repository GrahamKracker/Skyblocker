package de.hysky.skyblocker.skyblock.itemlist;

import de.hysky.skyblocker.mixins.accessors.RecipeBookWidgetAccessor;
import de.hysky.skyblocker.skyblock.todolist.ui.TodoListTab;
import de.hysky.skyblocker.utils.render.gui.SideTabButtonWidget;
import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.objects.ObjectObjectImmutablePair;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ContainerWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

@Environment(value = EnvType.CLIENT)
public class ItemListWidget extends RecipeBookWidget {
    private int parentWidth;
    private int parentHeight;
    private int leftOffset;

    private TabContainerWidget currentTabContent;
    private final List<Pair<SideTabButtonWidget, TabContainerWidget>> tabs = new ArrayList<>(3);
    private ItemListTab itemListTab;

    private static int currentTab = 0;

    public ItemListWidget() {
        super();
    }

    @Override
    public void initialize(int parentWidth, int parentHeight, MinecraftClient client, boolean narrow, AbstractRecipeScreenHandler<?, ?> craftingScreenHandler) {
        super.initialize(parentWidth, parentHeight, client, narrow, craftingScreenHandler);
        this.parentWidth = parentWidth;
        this.parentHeight = parentHeight;
        this.leftOffset = narrow ? 0 : 86;
        TextFieldWidget searchField = ((RecipeBookWidgetAccessor) this).getSearchField();
        int x = (parentWidth - 147) / 2 - leftOffset;
        int y = (parentHeight - 166) / 2;

        // Init all the tabs, content and the tab button on the left
        tabs.clear();

        // Item List
        itemListTab = new ItemListTab(x + 9, y + 9, this.client, searchField);
        SideTabButtonWidget itemListTabButton = new SideTabButtonWidget(x - 30, y + 3, currentTab == 0, new ItemStack(Items.CRAFTING_TABLE));
        itemListTabButton.setTooltip(Tooltip.of(Text.literal("Item List")));
        if (currentTab == 0) currentTabContent = itemListTab;
        tabs.add(new ObjectObjectImmutablePair<>(
                itemListTabButton,
                this.itemListTab));

        // Upcoming Events
        UpcomingEventsTab upcomingEventsTab = new UpcomingEventsTab(x + 9, y + 9, this.client);
        SideTabButtonWidget eventsTabButtonWidget = new SideTabButtonWidget(x - 30, y + 3 + 27, currentTab == 1, new ItemStack(Items.CLOCK));
        eventsTabButtonWidget.setTooltip(Tooltip.of(Text.literal("Upcoming Events")));
        if (currentTab == 1) currentTabContent = upcomingEventsTab;
        tabs.add(new ObjectObjectImmutablePair<>(
                eventsTabButtonWidget,
                upcomingEventsTab
        ));

		TodoListTab todoListTab = new TodoListTab(x + 9, y + 9, this.client);
		SideTabButtonWidget todoListTabButton = new SideTabButtonWidget(x - 30, y + 3 + (27 * 2), currentTab == 2, new ItemStack(Items.COMPASS));
		todoListTabButton.setTooltip(Tooltip.of(Text.literal("Todo List")));
		if (currentTab == 2) currentTabContent = todoListTab;
		tabs.add(new ObjectObjectImmutablePair<>(
				todoListTabButton,
				todoListTab
		));
    }

    @Override
    public void reset() {
        super.reset();
        if (itemListTab != null) itemListTab.setSearchField(((RecipeBookWidgetAccessor) this).getSearchField());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        if (this.isOpen()) {
            int i = (this.parentWidth - 147) / 2 - this.leftOffset;
            int j = (this.parentHeight - 166) / 2;
            // Draw the texture
            context.drawTexture(TEXTURE, i, j, 1, 1, 147, 166);
            // Draw the tab's content
            if (currentTabContent != null) currentTabContent.render(context, mouseX, mouseY, delta);
            // Draw the tab buttons
            for (Pair<SideTabButtonWidget, TabContainerWidget> tab : tabs) {
                tab.left().render(context, mouseX, mouseY, delta);
            }

        }
    }

    @Override
    public void drawTooltip(DrawContext context, int x, int y, int mouseX, int mouseY) {
        if (this.isOpen() && currentTabContent != null) {
            this.currentTabContent.drawTooltip(context, mouseX, mouseY);
        }
    }

	@Override
	public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
		if (this.isOpen() && this.client.player != null && !this.client.player.isSpectator() && this.currentTabContent != null) {
			return this.currentTabContent.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
		} else return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
	}

	@Override
	public boolean charTyped(char chr, int modifiers) {
		if (this.isOpen() && this.client.player != null && !this.client.player.isSpectator() && this.currentTabContent != null) {
			return this.currentTabContent.charTyped(chr, modifiers);
		} else return super.charTyped(chr, modifiers);
	}

	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
		if (this.isOpen() && this.client.player != null && !this.client.player.isSpectator() && this.currentTabContent != null) {
			return this.currentTabContent.keyPressed(keyCode, scanCode, modifiers);
		} else return super.keyPressed(keyCode, scanCode, modifiers);
	}

	@Override
	public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
		if (this.isOpen() && this.client.player != null && !this.client.player.isSpectator() && this.currentTabContent != null) {
			return this.currentTabContent.keyReleased(keyCode, scanCode, modifiers);
		} else return super.keyReleased(keyCode, scanCode, modifiers);
	}


	@Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.isOpen() && this.client.player != null && !this.client.player.isSpectator()) {
            // check if a tab is clicked
            for (Pair<SideTabButtonWidget, TabContainerWidget> tab : tabs) {
                if (tab.first().mouseClicked(mouseX, mouseY, button) && currentTabContent != tab.right()) {
                    for (Pair<SideTabButtonWidget, TabContainerWidget> tab2 : tabs) {
                        tab2.first().setToggled(false);
                    }
                    tab.first().setToggled(true);
                    currentTabContent = tab.right();
                    currentTab = tabs.indexOf(tab);
                    return true;
                }
            }
            // click the tab content
            if (currentTabContent != null) return currentTabContent.mouseClicked(mouseX, mouseY, button);
            else return false;
        } else return false;
    }

    /**
     * A container widget but with a fixed width and height and a drawTooltip method to implement
     */
    public abstract static class TabContainerWidget extends ContainerWidget {

        public TabContainerWidget(int x, int y, Text text) {
            super(x, y, 131, 150, text);
        }

        public abstract void drawTooltip(DrawContext context, int mouseX, int mouseY);
    }
}
