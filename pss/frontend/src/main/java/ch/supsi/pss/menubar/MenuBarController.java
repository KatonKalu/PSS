package ch.supsi.pss.menubar;

import ch.supsi.pss.PreferencesRepository;
import ch.supsi.pss.SketchController;
import ch.supsi.pss.drawFrame.DrawCanvasController;
import ch.supsi.pss.helpers.Alerter;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.stage.Stage;

import java.util.HashMap;

class MenuBarController {
    private PssMenuBar connectedMenuBar;
    private Stage controlledScene;
    private Node galleryRoot, drawRoot;

    private static MenuBarController instance;

    public static MenuBarController getInstance() {
        if (instance == null) {
            instance = new MenuBarController();
        }

        return instance;
    }

    private MenuBarController() {
    }

    public void setupController(Stage controlledStage, Node galleryRoot, Node drawRoot, PssMenuBar connectedMenuBar) {
        this.connectedMenuBar = connectedMenuBar;
        this.controlledScene = controlledStage;
        this.drawRoot = drawRoot;
        this.galleryRoot = galleryRoot;

        HashMap<String, Menu> menus = connectedMenuBar.getMenuMap();

        //not implemented linsteners
        menus.values().forEach( m -> m.getItems().forEach( menuItem -> menuItem.setOnAction( e -> Alerter.popNotImlementedAlert())));

        // 'View->gallery' listener
        menus.get("View").getItems().get(0).setOnAction(e -> {
            controlledStage.getScene().setRoot((Parent) galleryRoot);
        });

        // 'View->draw' listener
        menus.get("View").getItems().get(1).setOnAction(e -> {
            controlledStage.getScene().setRoot((Parent) drawRoot);
        });

        // 'Edit->Clear' listener
        menus.get("Edit").getItems().get(0).setOnAction(e -> {
            if (Alerter.popConfirmDialog("Are you sure?", "This operation will erase your work", "Are you ok with this?")) {
                DrawCanvasController.getInstance().getDrawCanvas().clearContent();
            }
        });

        // 'Help->About' listener
        menus.get("Help").getItems().get(0).setOnAction(e -> {
            Alerter.popInformationAlert(null,null,null);
        });

        // 'File->save' listener
        menus.get("File").getItems().get(1).setOnAction( e -> {
            System.out.println("Drawing saved");

            SketchController sketchController = new SketchController();
            PreferencesRepository.setRepository(controlledStage);

            sketchController.newSketch(DrawCanvasController.getInstance().getDrawCanvas());

            if(sketchController.getSketchService().saveSketch()){
                Alerter.popInformationAlert(null,null,"Sketch correctly saved.");
            }

        });

    }
}
