package org.virtushawk.rabbitmqplayground.view;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.data.binder.ValidationStatusChangeListener;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.mapstruct.factory.Mappers;
import org.virtushawk.rabbitmqplayground.entity.view.SalesItemViewModel;
import org.virtushawk.rabbitmqplayground.mapper.SalesItemToSalesItemViewModelMapper;
import org.virtushawk.rabbitmqplayground.service.SalesItemService;

import java.util.List;

@Route("")
@PageTitle("Buy item")
public class MainView extends VerticalLayout {

    private final transient SalesItemService salesItemService;
    private final transient SalesItemToSalesItemViewModelMapper salesItemToSalesItemViewModelMapper;

    private final ListBox<SalesItemViewModel> salesItemViewModelListBox;

    private final EmailField emailField;

    private final Button orderButton;

    public MainView(SalesItemService salesItemService) {
        this.salesItemService = salesItemService;
        this.salesItemToSalesItemViewModelMapper = Mappers.getMapper(SalesItemToSalesItemViewModelMapper.class);

        this.salesItemViewModelListBox = createSalesItemListBox();
        this.emailField = createEmailField();
        this.orderButton = createOrderButton();

        configureView();

        add(salesItemViewModelListBox, emailField, orderButton);
    }

    private List<SalesItemViewModel> getSalesItemModels() {
        return salesItemToSalesItemViewModelMapper.mapToListDTO(salesItemService.findAll());
    }

    private void configureView() {
        addClassName("main-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
    }

    private ListBox<SalesItemViewModel> createSalesItemListBox() {
        ListBox<SalesItemViewModel> bookList = new ListBox<>();
        bookList.setItems(getSalesItemModels());

        bookList.setRenderer(new ComponentRenderer<>(salesItem -> {
            HorizontalLayout row = new HorizontalLayout();
            row.setAlignItems(FlexComponent.Alignment.CENTER);

            Avatar avatar = new Avatar();
            avatar.setName(salesItem.getName());
            avatar.setImage(salesItem.getPicture());

            Span name = new Span(salesItem.getName());
            Span description = new Span(salesItem.getDescription());
            Span price = new Span(String.valueOf(salesItem.getPrice()));
            description.getStyle()
                    .set("color", "var(--lumo-secondary-text-color)")
                    .set("font-size", "var(--lumo-font-size-s)");

            VerticalLayout column = new VerticalLayout(name, description, price);
            column.setPadding(false);
            column.setSpacing(false);

            row.add(avatar, column);
            row.getStyle().set("line-height", "var(--lumo-line-height-m)");
            return row;
        }));

        bookList.addValueChangeListener((HasValue.ValueChangeListener<AbstractField.ComponentValueChangeEvent<ListBox<SalesItemViewModel>, SalesItemViewModel>>) event -> {
            if (event.getValue() != null) {
                emailField.setEnabled(true);
            }
        });
        return bookList;
    }

    private EmailField createEmailField() {
        EmailField field = new EmailField();
        field.setLabel("Email address");
        field.getElement().setAttribute("name", "email");
        field.setErrorMessage("Enter a valid email address");
        field.setClearButtonVisible(true);
        field.setEnabled(false);

        field.addValidationStatusChangeListener((ValidationStatusChangeListener<String>) event -> {
            if (event.getNewStatus() && !event.getSource().isEmpty()) {
                orderButton.setEnabled(true);
                return;
            }
            orderButton.setEnabled(false);
        });

        return field;
    }

    private Button createOrderButton() {
        Button button = new Button("Order");
        button.addThemeVariants(ButtonVariant.LUMO_LARGE);
        button.setEnabled(false);
        return button;
    }


}
