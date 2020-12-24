package com.houtarouoreki.hullethell.scripts.quests;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.houtarouoreki.hullethell.graphics.Axes;
import com.houtarouoreki.hullethell.graphics.Container;
import com.houtarouoreki.hullethell.graphics.Fonts;
import com.houtarouoreki.hullethell.numbers.Vector2;
import com.houtarouoreki.hullethell.ui.Label;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class QuestManager extends Container {
    public final List<Quest> quests = new ArrayList<>();

    public QuestManager() {
        setAnchor(new Vector2(1, 0));
        setOrigin(new Vector2(1, 0));
        setSize(new Vector2(100, 400));
    }

    public void registerQuest(Quest quest) {
        quests.add(quest);
        add(new QuestDrawable(quest));
    }

    private static class QuestDrawable extends Container {
        public final Quest quest;
        private final Label descriptionLabel;

        public QuestDrawable(Quest quest) {
            this.quest = quest;
            Label titleLabel = new Label();
            FreeTypeFontGenerator.FreeTypeFontParameter titleFont
                    = new FreeTypeFontGenerator.FreeTypeFontParameter();
            titleFont.borderColor = Color.WHITE;
            titleFont.borderWidth = 0.6f;
            titleFont.size = 14;
            titleLabel.font = Fonts.getFont("acme", titleFont);
            titleLabel.setText(quest.getTitle());
            add(titleLabel);

            descriptionLabel = new Label();
            FreeTypeFontGenerator.FreeTypeFontParameter descriptionFont
                    = new FreeTypeFontGenerator.FreeTypeFontParameter();
            descriptionFont.size = 12;
            descriptionLabel.font = Fonts.getFont("acme", descriptionFont);
            descriptionLabel.setPosition(new Vector2(0, 18));
            add(descriptionLabel);

            setRelativeSizeAxes(EnumSet.of(Axes.HORIZONTAL));
        }

        @Override
        protected void onUpdate(float delta) {
            super.onUpdate(delta);
            descriptionLabel.setText(quest.getDescription());
            float descriptionHeight = descriptionLabel.font.getLineHeight();
            for (int i = 0; i < quest.getDescription().length(); i++) {
                if (quest.getDescription().charAt(i) == '\n')
                    descriptionHeight += descriptionLabel.font.getLineHeight();
            }
            setSize(new Vector2(1, descriptionHeight));
        }
    }
}
