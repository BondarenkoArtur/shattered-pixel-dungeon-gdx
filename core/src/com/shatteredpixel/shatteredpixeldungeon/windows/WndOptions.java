/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015  Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2019 Evan Debenham
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package com.shatteredpixel.shatteredpixeldungeon.windows;

import com.shatteredpixel.shatteredpixeldungeon.SPDSettings;
import com.shatteredpixel.shatteredpixeldungeon.input.GameAction;
import com.shatteredpixel.shatteredpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.shatteredpixeldungeon.ui.RedButton;
import com.shatteredpixel.shatteredpixeldungeon.ui.RenderedTextMultiline;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;
import com.watabou.input.NoosaInputProcessor;

public class WndOptions extends Window {

	private static final int WIDTH_P = 120;
	private static final int WIDTH_L = 144;

	private static final int MARGIN 		= 2;
	private static final int BUTTON_HEIGHT	= 20;
	
	public WndOptions( String title, String message, String... options ) {
		super();

		int width = SPDSettings.landscape() ? WIDTH_L : WIDTH_P;

		RenderedTextMultiline tfTitle = PixelScene.renderMultiline( title, 9 );
		tfTitle.hardlight( TITLE_COLOR );
		tfTitle.setPos(MARGIN, MARGIN);
		tfTitle.maxWidth(width - MARGIN * 2);
		add( tfTitle );
		
		RenderedTextMultiline tfMesage = PixelScene.renderMultiline( 6 );
		tfMesage.text(message, width - MARGIN * 2);
		tfMesage.setPos( MARGIN, tfTitle.top() + tfTitle.height() + MARGIN );
		add( tfMesage );
		
		float pos = tfMesage.bottom() + MARGIN;
		
		for (int i=0; i < options.length; i++) {
			final int index = i;
			OptionButton btn = new OptionButton( "S" + (i + 1) + ": " + options[i], GameAction.SLOT, i+1) {
				@Override
				protected void onClick() {
					hide();
					onSelect( index );
				}
			};
			btn.setRect( MARGIN, pos, width - MARGIN * 2, BUTTON_HEIGHT );
			add( btn );
			
			pos += BUTTON_HEIGHT + MARGIN;
		}
		
		resize( width, (int)pos );
	}
	
	protected void onSelect( int index ) {};

	private class OptionButton extends RedButton {

		private final int slot;

		private OptionButton(String label, GameAction action, int slot) {
			super(label, action);
			this.slot = slot;
		}


		@Override
		protected boolean onKeyUp(NoosaInputProcessor.Key<GameAction> key) {
			if (active && key.action.equals(GameAction.SLOT)) {
				if (slot == (key.code >> 8)) {
					onClick();
					return true;
				} else {
					return false;
				}
			} else {
				return super.onKeyUp(key);
			}
		}
	}
}
