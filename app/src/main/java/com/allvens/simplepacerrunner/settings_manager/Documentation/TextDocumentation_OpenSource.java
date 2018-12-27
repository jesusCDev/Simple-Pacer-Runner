package com.allvens.simplepacerrunner.settings_manager.Documentation;

import android.content.Context;

import com.allvens.simplepacerrunner.R;

public class TextDocumentation_OpenSource extends TextDocumentation_Manager {

    public TextDocumentation_OpenSource(Context context) {
        super(context);

        create_Title(R.string.open_source_title);
        create_Paragraph(R.string.open_source_date);

        create_Paragraph(R.string.open_source_summary);
        create_SubTitle(R.string.open_source_section_1_sub_title);
        create_Paragraph(R.string.open_source_section_1_summary);
    }
}
