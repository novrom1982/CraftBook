/*
 * CraftBook Copyright (C) EngineHub and Contributors <https://enginehub.org/>
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program. If not,
 * see <http://www.gnu.org/licenses/>.
 */

package org.enginehub.craftbook.mechanics.ic.gates.world.miscellaneous;

import org.bukkit.Server;
import org.bukkit.entity.ExperienceOrb;
import org.enginehub.craftbook.ChangedSign;
import org.enginehub.craftbook.mechanics.ic.AbstractIC;
import org.enginehub.craftbook.mechanics.ic.AbstractICFactory;
import org.enginehub.craftbook.mechanics.ic.ChipState;
import org.enginehub.craftbook.mechanics.ic.IC;
import org.enginehub.craftbook.mechanics.ic.ICFactory;
import org.enginehub.craftbook.mechanics.ic.RestrictedIC;

public class XPSpawner extends AbstractIC {

    public XPSpawner(Server server, ChangedSign sign, ICFactory factory) {
        super(server, sign, factory);
    }

    int amount, orbs;

    @Override
    public void load() {

        try {
            amount = Integer.parseInt(getLine(2));
        } catch (Exception e) {
            amount = 1;
        }

        try {
            orbs = Integer.parseInt(getLine(3));
        } catch (Exception e) {
            orbs = 1;
        }
    }

    @Override
    public String getTitle() {
        return "Experience Orb Spawner";
    }

    @Override
    public String getSignTitle() {
        return "XP SPAWNER";
    }

    @Override
    public void trigger(ChipState chip) {

        if (chip.getInput(0)) {

            for (int i = 0; i < orbs; i++) {
                ExperienceOrb orb = getLocation().getWorld().spawn(getLocation().add(0.5, 1.5, 0.5), ExperienceOrb.class);
                orb.setExperience(amount);
            }
        }
    }

    public static class Factory extends AbstractICFactory implements RestrictedIC {

        public Factory(Server server) {

            super(server);
        }

        @Override
        public IC create(ChangedSign sign) {

            return new XPSpawner(getServer(), sign, this);
        }

        @Override
        public String getShortDescription() {

            return "Spawns an XP Orb.";
        }

        @Override
        public String[] getLineHelp() {

            return new String[] { "amount of xp", "amount of orbs" };
        }
    }
}
