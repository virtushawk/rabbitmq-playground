package org.virtushawk.rabbitmqplayground.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.mapstruct.factory.Mappers;
import org.virtushawk.rabbitmqplayground.entity.view.OrderViewModel;
import org.virtushawk.rabbitmqplayground.mapper.OrderToOrderViewModelMapper;
import org.virtushawk.rabbitmqplayground.mapper.OrderViewModelToOrderMapper;
import org.virtushawk.rabbitmqplayground.service.OrderService;

@Route("/support")
@PageTitle("Support")
public class SupportView extends VerticalLayout {
    Grid<OrderViewModel> grid = new Grid<>(OrderViewModel.class);
    SupportForm form;

    private final transient OrderService orderService;

    private final transient OrderToOrderViewModelMapper orderToOrderViewModelMapper;
    private final transient OrderViewModelToOrderMapper orderViewModelToOrderMapper;

    public SupportView(OrderService orderService) {
        this.orderService = orderService;
        this.orderToOrderViewModelMapper = Mappers.getMapper(OrderToOrderViewModelMapper.class);
        this.orderViewModelToOrderMapper = Mappers.getMapper(OrderViewModelToOrderMapper.class);

        addClassName("support-view");
        setSizeFull();
        configureGrid();
        configureForm();

        add( getContent());
        updateList();
        closeEditor();
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, form);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, form);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }

    private void configureForm() {
        form = new SupportForm();
        form.setWidth("25em");

        form.addReturnOrderListener(this::returnOrder);
        form.addDeleteListener(this::deleteOrder);
        form.addArchiveListener(this::archiveOrder);
    }

    private void configureGrid() {
        grid.addClassNames("support-grid");
        grid.setSizeFull();
        grid.addColumn(order -> order.getReceipt().getTotalPrice()).setHeader("price");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event ->
                editOrder(event.getValue()));
    }

    public void editOrder(OrderViewModel order) {
        if (order == null) {
            closeEditor();
        } else {
            form.setOrder(order);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setOrder(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void updateList() {
        grid.setItems(orderToOrderViewModelMapper.mapToListDTO(orderService.getFailedOrders()));
    }

    private void returnOrder(SupportForm.ReturnOrderEvent event) {
        orderService.retryFailedOrder(orderViewModelToOrderMapper.mapToEntity(event.getOrder()));
        updateList();
        closeEditor();
    }

    private void deleteOrder(SupportForm.DeleteEvent event) {
        orderService.delete(orderViewModelToOrderMapper.mapToEntity(event.getOrder()));
        updateList();
        closeEditor();
    }

    private void archiveOrder(SupportForm.ArchiveEvent event) {
        orderService.archiveOrder(orderViewModelToOrderMapper.mapToEntity(event.getOrder()));
        updateList();
        closeEditor();
    }
}
