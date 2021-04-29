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

package org.enginehub.craftbook.mechanics.ic.families;

import com.sk89q.worldedit.util.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.enginehub.craftbook.ChangedSign;
import org.enginehub.craftbook.bukkit.util.CraftBookBukkitUtil;
import org.enginehub.craftbook.mechanics.ic.AbstractChipState;
import org.enginehub.craftbook.mechanics.ic.AbstractICFamily;
import org.enginehub.craftbook.mechanics.ic.ChipState;
import org.enginehub.craftbook.util.SignUtil;

/**
 * Handles detection for the variable-input variable-output family.
 *
 * @author Lymia
 */
public class FamilyVIVO extends AbstractICFamily {

    @Override
    public ChipState detect(Location source, ChangedSign sign) {

        return new ChipStateVIVO(source, sign);
    }

    @Override
    public ChipState detectSelfTriggered(Location source, ChangedSign sign) {

        return new ChipStateVIVO(source, sign, true);
    }

    public static class ChipStateVIVO extends AbstractChipState {

        public ChipStateVIVO(Location source, ChangedSign sign) {

            super(source, sign, false);
        }

        public ChipStateVIVO(Location source, ChangedSign sign, boolean selfTriggered) {

            super(source, sign, selfTriggered);
        }

        @Override
        protected Block getBlock(int pin) {

            BlockFace fback = SignUtil.getBack(CraftBookBukkitUtil.toSign(sign).getBlock());
            Block backBlock = CraftBookBukkitUtil.toSign(sign).getBlock().getRelative(fback);

            switch (pin) {
                case 0:
                    return SignUtil.getFrontBlock(CraftBookBukkitUtil.toSign(sign).getBlock());
                case 1:
                    return SignUtil.getLeftBlock(CraftBookBukkitUtil.toSign(sign).getBlock());
                case 2:
                    return SignUtil.getRightBlock(CraftBookBukkitUtil.toSign(sign).getBlock());
                case 3:
                    return backBlock.getRelative(fback);
                case 4:
                    return backBlock.getRelative(SignUtil.getCounterClockWise(fback));
                case 5:
                    return backBlock.getRelative(SignUtil.getClockWise(fback));
                default:
                    return null;

            }

        }

        @Override
        public boolean getInput(int inputIndex) {

            return get(inputIndex);
        }

        @Override
        public boolean getOutput(int outputIndex) {

            return get(outputIndex + 3);
        }

        @Override
        public void setOutput(int outputIndex, boolean value) {

            set(outputIndex + 3, value);
        }

        @Override
        public int getInputCount() {

            return 3;
        }

        @Override
        public int getOutputCount() {

            return 3;
        }

    }

    @Override
    public String getName() {
        return "VIVO";
    }
}
