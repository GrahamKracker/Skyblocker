package de.hysky.skyblocker.skyblock.todolist;

import de.hysky.skyblocker.skyblock.waypoint.AbstractWaypointsScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.world.WorldListWidget;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.text.Text;

import java.util.List;

@Environment(EnvType.CLIENT)
public class TodoListScroll extends ElementListWidget<TodoListScroll.Entry>
{
	private final HandledScreen<?> parent;

	public TodoListScroll(HandledScreen<?> parent, MinecraftClient client, int width, int height, int y, int itemHeight) {
		super(client, width, height, y, itemHeight);
		this.parent = parent;
	}


	@Environment(EnvType.CLIENT)
	public static class Entry extends ElementListWidget.Entry<TodoListScroll.Entry>
	{
		@Override
		public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta)
		{

		}

		@Override
		public List<? extends Selectable> selectableChildren() {
			return List.of();
		}

		@Override
		public List<? extends Element> children() {
			return List.of();
		}
	}
}
