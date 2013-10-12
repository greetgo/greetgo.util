package kz.greetgo.sgwt.base;

import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;

public class Snippet {
	public static final String SAVE = "Сохранить";
	public static final String CANCEL = "Отменить";
	public static final String CLOSE = "Закрыть";
	public static final String CLEAN = "Очистить";
	public static final String REFRESH = "Обновить";
	public static final String APPLY = "Применить";
	public static final String PLUS = "+";
	public static final String MINUS = "-";
	public static final String ELLIPSIS = "...";
	public static final String UP = "&#9650";
	public static final String DOWN = "&#9660";
	public static final String SEARCH = "Искать";
	public static final String REPORT = "Сформировать отчёт";

	public static final int GRAIN = 8;
	public static final int TOOL = 3 * GRAIN;

	protected Snippet() {
	}

	public static final HLayout hl(Canvas... members) {
		HLayout hl = new HLayout();
		hl.setMembers(members);
		return hl;
	}

	public static final VLayout vl(Canvas... members) {
		VLayout vl = new VLayout();
		vl.setMembers(members);
		return vl;
	}

	public static final HLayout toolbar(HLayout layout) {
		layout.setMembersMargin(TOOL);
		layout.setMargin(GRAIN);
		layout.setAutoHeight();
		layout.setWidth100();
		return layout;
	}

	public static final VLayout toolbar(VLayout layout) {
		layout.setMembersMargin(GRAIN);
		layout.setMargin(GRAIN);
		layout.setAutoWidth();
		layout.setHeight100();
		return layout;
	}

	public static final void items(Window window, Canvas... items) {
		for (Canvas item : items) {
			window.addItem(item);
		}
	}

	public static final DynamicForm form(FormItem... items) {
		DynamicForm form = new DynamicForm();
		form.setItems(items);
		return form;
	}

	public static TextItem text(NamedTitle namedTitle) {
		return new TextItem(namedTitle.name, namedTitle.title);
	}

	public static ComboBoxItem combo(NamedTitle namedTitle) {
		return new ComboBoxItem(namedTitle.name, namedTitle.title);
	}

	public static StaticTextItem staticText(NamedTitle namedTitle2) {
		return new StaticTextItem(namedTitle2.name, namedTitle2.title);
	}

	public static final ListGridField field(NamedTitle namedTitle) {
		return new ListGridField(namedTitle.name, namedTitle.title);
	}

	public static final SectionStack headed(String header, Canvas... items) {
		SectionStack stack = new SectionStack();
		SectionStackSection section = new SectionStackSection(header);
		section.setCanCollapse(false);
		section.setExpanded(true);
		section.setItems(items);
		stack.setSections(section);
		return stack;
	}
}
