package xyz.xenondevs.invui.gui;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.xenondevs.invui.gui.structure.Structure;
import xyz.xenondevs.invui.item.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * An {@link AbstractPagedGui} that is filled with {@link Item Items}.
 *
 * @see PagedNestedGuiImpl
 * @see PagedInventoriesGuiImpl
 */
public class PagedItemsGuiImpl extends AbstractPagedGui<Item> {
    
    /**
     * Creates a new {@link PagedItemsGuiImpl}.
     *
     * @param width            The width of this Gui.
     * @param height           The height of this Gui.
     * @param items            The {@link Item Items} to use as pages.
     * @param contentListSlots The slots where content should be displayed.
     */
    public PagedItemsGuiImpl(int width, int height, @Nullable List<@NotNull Item> items, int... contentListSlots) {
        super(width, height, false, contentListSlots);
        setContent(items);
    }
    
    /**
     * Creates a new {@link PagedItemsGuiImpl}.
     *
     * @param items     The {@link Item Items} to use as pages.
     * @param structure The {@link Structure} to use.
     */
    public PagedItemsGuiImpl(@Nullable List<@NotNull Item> items, @NotNull Structure structure) {
        super(structure.getWidth(), structure.getHeight(), false, structure);
        setContent(items);
    }
    
    @Override
    public void bake() {
        List<Item> items = content;
        int contentSize = getContentListSlots().length;
        List<List<SlotElement>> pages = new ArrayList<>();
        
        int pageAmount = items.size() / contentSize;
        for (int pageIdx = 0; pageIdx < pageAmount; pageIdx++) {
            int from = pageIdx * contentSize;
            int to = Math.min(from + contentSize, items.size());
            
            ArrayList<SlotElement> page = new ArrayList<>(contentSize);
            for (int i = from; i < to; i++) {
                page.add(new SlotElement.ItemSlotElement(items.get(i)));
            }
            pages.add(page);
        }
        
        this.pages = pages;
        update();
    }
    
    public static final class Builder extends AbstractBuilder<Item> {
        
        @Override
        public @NotNull PagedGui<Item> build() {
            if (structure == null)
                throw new IllegalStateException("Structure is not defined.");
            
            var gui = new PagedItemsGuiImpl(content, structure);
            applyModifiers(gui);
            return gui;
        }
        
    }
    
}
