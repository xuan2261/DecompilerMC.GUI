/*
 * Decompiled with CFR 0.150.
 */
package net.minecraft.client.gui.screens;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Arrays;
import java.util.stream.Stream;
import net.minecraft.client.Option;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.OptionsList;
import net.minecraft.client.gui.screens.OptionsSubScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.TranslatableComponent;

public class MouseSettingsScreen
extends OptionsSubScreen {
    private OptionsList list;
    private static final Option[] OPTIONS = new Option[]{Option.SENSITIVITY, Option.INVERT_MOUSE, Option.MOUSE_WHEEL_SENSITIVITY, Option.DISCRETE_MOUSE_SCROLL, Option.TOUCHSCREEN};

    public MouseSettingsScreen(Screen screen, Options options) {
        super(screen, options, new TranslatableComponent("options.mouse_settings.title"));
    }

    @Override
    protected void init() {
        this.list = new OptionsList(this.minecraft, this.width, this.height, 32, this.height - 32, 25);
        if (InputConstants.isRawMouseInputSupported()) {
            this.list.addSmall((Option[])Stream.concat(Arrays.stream(OPTIONS), Stream.of(Option.RAW_MOUSE_INPUT)).toArray(Option[]::new));
        } else {
            this.list.addSmall(OPTIONS);
        }
        this.children.add(this.list);
        this.addButton(new Button(this.width / 2 - 100, this.height - 27, 200, 20, CommonComponents.GUI_DONE, button -> {
            this.options.save();
            this.minecraft.setScreen(this.lastScreen);
        }));
    }

    @Override
    public void render(PoseStack poseStack, int n, int n2, float f) {
        this.renderBackground(poseStack);
        this.list.render(poseStack, n, n2, f);
        MouseSettingsScreen.drawCenteredString(poseStack, this.font, this.title, this.width / 2, 5, 0xFFFFFF);
        super.render(poseStack, n, n2, f);
    }
}
