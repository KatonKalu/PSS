package ch.supsi.labingsw1.model;

import javafx.geometry.Insets;
import javafx.scene.control.ListCell;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;

import javafx.scene.paint.*;

public class StyledSelectableCell<T> extends ListCell<T> {
    private Background selBG;
    private Background idleBG;
    private Paint idleTextCol;
    private Paint selTextCol;

    public StyledSelectableCell(Colors idleBG) {
        assert idleBG.getComplementary() != null;
        this.selBG = new Background(new BackgroundFill(idleBG.getComplementary().getVal(), CornerRadii.EMPTY, Insets.EMPTY));
        this.idleBG = new Background(new BackgroundFill(idleBG.getVal(), CornerRadii.EMPTY, Insets.EMPTY));

        this.idleTextCol = idleBG.getSuggestedTextColor();
        this.selTextCol = idleBG.getComplementary().getSuggestedTextColor();
    }

    @Override
    protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);

        // This execute to update each 'empty cell' where in fact the item is null
        if (item == null) {
            // setting transparent bg
            this.setBackground(
                    new Background(
                            new BackgroundFill(
                                    new Color(1., 1., 1., 0.),
                                    CornerRadii.EMPTY,
                                    Insets.EMPTY
                            )));

            setText("");
        } else {

            if (this.isSelected()) {
                setBG(this.selBG);
                setTextFill(this.selTextCol);
            } else {
                setBG(this.idleBG);
                setTextFill(this.idleTextCol);
            }

            setText(item.toString());
        }
    }

    private void setBG(Background c) {
        this.setBackground(c);
    }
}
