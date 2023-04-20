package org.virtushawk.rabbitmqplayground.view;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;
import org.virtushawk.rabbitmqplayground.entity.view.OrderViewModel;

public class SupportForm extends FormLayout {
    Binder<OrderViewModel> binder = new BeanValidationBinder<>(OrderViewModel.class);
    TextField uuid = new TextField("uuid");
    EmailField email = new EmailField("email");

    Button returnOrder = new Button("Return");
    Button archive = new Button("Archive");
    Button delete = new Button("Delete");

    public SupportForm() {
        addClassName("support-form");
        binder.bindInstanceFields(this);

        uuid.setReadOnly(true);
        email.setReadOnly(true);

        add(uuid, email, createButtonsLayout());
    }

    private HorizontalLayout createButtonsLayout() {
        returnOrder.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        archive.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        returnOrder.addClickShortcut(Key.ENTER);
        archive.addClickShortcut(Key.ESCAPE);

        returnOrder.addClickListener(event -> fireEvent(new ReturnOrderEvent(this, binder.getBean())));
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, binder.getBean())));
        archive.addClickListener(event -> fireEvent(new ArchiveEvent(this, binder.getBean())));

        return new HorizontalLayout(returnOrder, delete, archive);
    }

    public void setOrder(OrderViewModel order) {
        binder.setBean(order);
    }


    public abstract static class SupportFormEvent extends ComponentEvent<SupportForm> {
        private final transient OrderViewModel order;

        protected SupportFormEvent(SupportForm source, OrderViewModel order) {
            super(source, false);
            this.order = order;
        }

        public OrderViewModel getOrder() {
            return order;
        }
    }

    public static class ReturnOrderEvent extends SupportFormEvent {
        ReturnOrderEvent(SupportForm source, OrderViewModel order) {
            super(source, order);
        }
    }

    public static class DeleteEvent extends SupportFormEvent {
        DeleteEvent(SupportForm source, OrderViewModel order) {
            super(source, order);
        }

    }

    public static class ArchiveEvent extends SupportFormEvent {
        ArchiveEvent(SupportForm source, OrderViewModel order) {
            super(source, order);
        }
    }

    public Registration addDeleteListener(ComponentEventListener<DeleteEvent> listener) {
        return addListener(DeleteEvent.class, listener);
    }

    public Registration addReturnOrderListener(ComponentEventListener<ReturnOrderEvent> listener) {
        return addListener(ReturnOrderEvent.class, listener);
    }
    public Registration addArchiveListener(ComponentEventListener<ArchiveEvent> listener) {
        return addListener(ArchiveEvent.class, listener);
    }

}
