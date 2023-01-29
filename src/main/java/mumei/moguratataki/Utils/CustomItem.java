package mumei.moguratataki.Utils;

import io.papermc.paper.enchantments.EnchantmentRarity;
import mumei.moguratataki.Moguratataki;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.EntityCategory;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class CustomItem {
    private final Material material;
    private final String title;
    private List<String> lore;
    private Consumer<PlayerInteractEvent> onClick = (event) -> {
    };
    private boolean allowEnchantGlow = false;

    public CustomItem(@Nonnull Material material,
                      @Nonnull String title) {
        this.material = material;
        this.title = title;
    }

    public Material getMaterial() {
        return material;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getLore() {
        return lore;
    }

    public void setLore(List<String> lore) {
        this.lore = lore;
    }

    public void setLore(String lore) {
        this.lore = Arrays.asList(lore.split("\n"));
    }

    public Consumer<PlayerInteractEvent> getOnClick() {
        return onClick;
    }

    public void setOnClick(Consumer<PlayerInteractEvent> onClick) {
        this.onClick = onClick;
    }

    public boolean isAllowEnchantGlow() {
        return allowEnchantGlow;
    }

    public void setAllowEnchantGlow(boolean allowEnchantGlow) {
        this.allowEnchantGlow = allowEnchantGlow;
    }

    public ItemStack getAsItemStack() {
        // Create ItemStack
        ItemStack customItem = new ItemStack(material);
        ItemMeta meta = customItem.getItemMeta();

        meta.setDisplayName(title);
        meta.setLore(lore);

        customItem.setItemMeta(meta);

        if (isAllowEnchantGlow())
            customItem.addUnsafeEnchantment(new Glow(), 1);

        return customItem;
    }

    private final static class Glow extends Enchantment {
        public Glow() {
            super(new NamespacedKey(Moguratataki.getplugin(), "Moguratataki.glow"));
        }

        @Override
        public boolean canEnchantItem(@Nonnull ItemStack arg0) {
            return false;
        }

        @Override
        public @Nonnull
        Component displayName(int i) {
            return Component.text("");
        }

        @Override
        public boolean isTradeable() {
            return false;
        }

        @Override
        public boolean isDiscoverable() {
            return false;
        }

        @Override
        public @Nonnull
        EnchantmentRarity getRarity() {
            return EnchantmentRarity.RARE;
        }

        @Override
        public float getDamageIncrease(int i, @Nonnull EntityCategory entityCategory) {
            return 0;
        }

        @Override
        public @Nonnull
        Set<EquipmentSlot> getActiveSlots() {
            return new HashSet<>();
        }

        @Override
        public boolean conflictsWith(@Nonnull Enchantment arg0) {
            return false;
        }

        @Override
        public EnchantmentTarget getItemTarget() {
            return EnchantmentTarget.ALL;
        }

        @Override
        public boolean isTreasure() {
            return false;
        }

        @Override
        public boolean isCursed() {
            return false;
        }

        @Override
        public int getMaxLevel() {
            return 0;
        }

        @Override
        public @Nonnull
        String getName() {
            return "";
        }

        @Override
        public int getStartLevel() {
            return 0;
        }

        @Override
        public @Nonnull
        String translationKey() {
            return "";
        }
    }
}
