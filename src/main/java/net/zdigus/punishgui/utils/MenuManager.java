package net.zdigus.punishgui.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MenuManager {
    private Map<UUID, Menu> openedMenus = new HashMap<>();

    public void add(final UUID uuid, final Menu menu) {
        openedMenus.put(uuid, menu);
    }

    public Menu get(final UUID uuid) {
        return openedMenus.get(uuid);
    }

    public boolean contains(final UUID uuid) {
        return openedMenus.containsKey(uuid);
    }

    public void remove(final UUID uuid) {
        openedMenus.remove(uuid);
    }

    public void clear() {
        openedMenus.clear();
    }
}
