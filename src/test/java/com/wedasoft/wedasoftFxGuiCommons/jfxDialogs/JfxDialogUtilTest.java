package com.wedasoft.wedasoftFxGuiCommons.jfxDialogs;

import com.wedasoft.wedasoftFxGuiCommons.shared.Scene1Controller;
import com.wedasoft.wedasoftFxGuiCommons.shared.SimpleJavaFxTestBase;
import javafx.geometry.Dimension2D;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.List;
import java.util.function.Consumer;

import static com.wedasoft.wedasoftFxGuiCommons.jfxDialogs.JfxDialogUtil.*;
import static com.wedasoft.wedasoftFxGuiCommons.shared.UserRobotUtil.typeKeysAfterSeconds;
import static javafx.scene.input.KeyCode.ESCAPE;
import static org.assertj.core.api.Assertions.assertThat;

class JfxDialogUtilTest extends SimpleJavaFxTestBase {

    private final String loremIpsumText = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. ";

    @Nested
    class FxmlDialogTest {

        private Stage stage;
        private int valueChangedByCallback;

        @Test
        public void createAndShowFxmlDialogTest() throws Exception {
            pressKeyAsyncInOtherThread(1000, ESCAPE);
            runOnJavaFxThreadAndJoin(() -> JfxDialogUtil.createAndShowFxmlDialog(
                    "My Dialog",
                    true,
                    false,
                    getClass().getResource("/com/wedasoft/wedasoftFxGuiCommons/sceneUtil/scene1.fxml"),
                    new Dimension2D(600, 500),
                    (Consumer<Scene1Controller>) consumer -> consumer.init("myparamter1"),
                    () -> valueChangedByCallback = 52));
        }

        @Test
        public void createFxmlDialogTest() throws Exception {
            pressKeyAsyncInOtherThread(2000, ESCAPE);
            runOnJavaFxThreadAndJoin(() -> {
                stage = JfxDialogUtil.createFxmlDialog(
                        "My Dialog",
                        true,
                        false,
                        getClass().getResource("/com/wedasoft/wedasoftFxGuiCommons/sceneUtil/scene1.fxml"),
                        new Dimension2D(600, 500),
                        (Consumer<Scene1Controller>) consumer -> consumer.init("myparamter1"),
                        () -> valueChangedByCallback = 52);
                stage.showAndWait();
            });
            assertThat(stage).isNotNull();
            assertThat(stage.getTitle()).isEqualTo("My Dialog");
            assertThat(stage.getModality()).isEqualTo(Modality.APPLICATION_MODAL);
            assertThat(stage.isResizable()).isFalse();
            assertThat(stage.getScene().getWidth()).isEqualTo(600);
            assertThat(stage.getScene().getHeight()).isEqualTo(500);
            assertThat(((TextField) stage.getScene().lookup("#passedParameterTf")).getText()).isEqualTo("myparamter1");
            assertThat(valueChangedByCallback).isEqualTo(52);
        }

    }

    @Nested
    class InformationDialogTest {
        private Alert informationDialog;

        @Test
        void createInformationDialog_withoutHeader() throws Exception {
            runOnJavaFxThreadAndJoin(() -> {
                informationDialog = createInformationDialog(loremIpsumText);
                informationDialog.show();
                informationDialog.close();
            });
            assertThat(informationDialog.getTitle()).isEqualTo("Information");
            assertThat(informationDialog.getHeaderText()).isNull();
            assertThat(informationDialog.getContentText()).isEqualTo(loremIpsumText);
        }

        @Test
        void createInformationDialog_withHeader() throws Exception {
            runOnJavaFxThreadAndJoin(() -> {
                informationDialog = createInformationDialog(loremIpsumText, "HeaderText");
                informationDialog.show();
                informationDialog.close();
            });
            assertThat(informationDialog.getTitle()).isEqualTo("Information");
            assertThat(informationDialog.getHeaderText()).isEqualTo("HeaderText");
            assertThat(informationDialog.getContentText()).isEqualTo(loremIpsumText);

            runOnJavaFxThreadAndJoin(() -> {
                informationDialog = createInformationDialog(loremIpsumText, null);
                informationDialog.show();
                informationDialog.close();
            });
            assertThat(informationDialog.getTitle()).isEqualTo("Information");
            assertThat(informationDialog.getHeaderText()).isNull();
            assertThat(informationDialog.getContentText()).isEqualTo(loremIpsumText);
        }
    }

    @Nested
    class WarningDialogTest {
        private Alert warningDialog;

        @Test
        void createWarningDialog_withoutHeader() throws Exception {
            runOnJavaFxThreadAndJoin(() -> {
                warningDialog = createWarningDialog(loremIpsumText);
                warningDialog.show();
                warningDialog.close();
            });
            assertThat(warningDialog.getTitle()).isEqualTo("Warning");
            assertThat(warningDialog.getHeaderText()).isNull();
            assertThat(warningDialog.getContentText()).isEqualTo(loremIpsumText);
        }

        @Test
        void createWarningDialog_withHeader() throws Exception {
            runOnJavaFxThreadAndJoin(() -> {
                warningDialog = createWarningDialog(loremIpsumText, "HeaderText");
                warningDialog.show();
                warningDialog.close();
            });
            assertThat(warningDialog.getTitle()).isEqualTo("Warning");
            assertThat(warningDialog.getHeaderText()).isEqualTo("HeaderText");
            assertThat(warningDialog.getContentText()).isEqualTo(loremIpsumText);
        }

    }

    @Nested
    class ErrorDialogTest {
        private Alert errorDialog;

        @Test
        void createErrorDialog_withoutStacktrace() throws Exception {
            runOnJavaFxThreadAndJoin(() -> {
                errorDialog = createErrorDialog(loremIpsumText);
                errorDialog.show();
                errorDialog.close();
            });
            assertThat(errorDialog.getTitle()).isEqualTo("Error");
            assertThat(errorDialog.getHeaderText()).isNull();
            assertThat(errorDialog.getContentText()).isEqualTo(loremIpsumText);
        }

        @Test
        void createErrorDialog_withException() throws Exception {
            runOnJavaFxThreadAndJoin(() -> {
                errorDialog = createErrorDialog(loremIpsumText, new NullPointerException(loremIpsumText));
                errorDialog.show();
                errorDialog.close();
            });
            assertThat(errorDialog.getTitle()).isEqualTo("Error");
            assertThat(errorDialog.getHeaderText()).isEqualTo(loremIpsumText);
            assertThat(((TextArea) errorDialog.getDialogPane().getContent()).getText()).isNotEmpty();
        }

    }

    @Nested
    class DialogWithColumnsTest {
        private Alert dialog;

        @Test
        void createErrorDialog_withoutStacktrace() throws Exception {
            runOnJavaFxThreadAndJoin(() -> {
                dialog = JfxDialogUtil.createDialogWithColumns("Dialog with columns", 3, 20, List.of(
                        new Label("Label1"),
                        new Label("Label2"),
                        new Label("Label3"),
                        new Label("Label4"),
                        new Label("Label5")));
                dialog.show();
                dialog.close();
            });
            assertThat(dialog.getTitle()).isEqualTo("Dialog with columns");
            assertThat(dialog.getHeaderText()).isNull();
            GridPane grid = (GridPane) dialog.getDialogPane().getContent();
            assertThat(grid.getColumnCount()).isEqualTo(3);
            assertThat(grid.getRowCount()).isEqualTo(2);
            assertThat(grid.getVgap()).isEqualTo(20);
            assertThat(grid.getHgap()).isEqualTo(20);
            for (int i = 0; i < grid.getChildren().size(); i++) {
                assertThat(((Label) grid.getChildren().get(i)).getText()).isEqualTo("Label" + (i + 1));
            }
        }

    }

    @Nested
    class ConfirmDialogTest {

        private boolean result;

        @Timeout(10)
        @Test
        void createConfirmDialog_test() throws Exception {
            runOnJavaFxThreadAndJoin(() -> {
                typeKeysAfterSeconds(List.of(ESCAPE), 2);
                result = displayConfirmDialogAndGetResult("headerText", "contentTest");
            });
            assertThat(result).isFalse();

            runOnJavaFxThreadAndJoin(() -> {
                typeKeysAfterSeconds(List.of(KeyCode.ENTER), 2);
                result = displayConfirmDialogAndGetResult("headerText", "contentTest");
            });
            assertThat(result).isTrue();
        }

    }

    @Nested
    class InputDialogTest {
        private String result;

        @Timeout(10)
        @Test
        void createInputDialog_withInput() throws Exception {
            runOnJavaFxThreadAndJoin(() -> {
                typeKeysAfterSeconds(List.of(KeyCode.A, KeyCode.B, KeyCode.C), 1);
                typeKeysAfterSeconds(List.of(KeyCode.ENTER), 2);
                result = displayInputDialogAndGetResult("dummy text");
            });
            assertThat(result).isEqualTo("abc");

            runOnJavaFxThreadAndJoin(() -> {
                typeKeysAfterSeconds(List.of(KeyCode.A, KeyCode.B, KeyCode.C), 1);
                typeKeysAfterSeconds(List.of(ESCAPE), 2);
                result = displayInputDialogAndGetResult("dummy text");
            });
            assertThat(result).isEqualTo("");

            runOnJavaFxThreadAndJoin(() -> {
                typeKeysAfterSeconds(List.of(ESCAPE), 1);
                result = displayInputDialogAndGetResult("dummy text");
            });
            assertThat(result).isEqualTo("");
        }

    }

    @Nested
    class CloseStageDialogTest {

        private Stage stageToClose;

        @Test
        void displayCloseStageDialog() throws Exception {
            runOnJavaFxThreadAndJoin(() -> {
                stageToClose = new Stage();
                stageToClose.setWidth(300);
                stageToClose.setHeight(300);
                stageToClose.show();
            });
            assertThat(stageToClose.isShowing()).isTrue();

            typeKeysAfterSeconds(List.of(ESCAPE), 1);
            runOnJavaFxThreadAndJoin(() -> JfxDialogUtil.displayCloseStageDialog(stageToClose, "Close Stage Dialog Title", "Close Stage Dialog Text"));
            assertThat(stageToClose.isShowing()).isTrue();

            typeKeysAfterSeconds(List.of(KeyCode.ENTER), 1);
            runOnJavaFxThreadAndJoin(() -> JfxDialogUtil.displayCloseStageDialog(stageToClose));
            assertThat(stageToClose.isShowing()).isFalse();
        }
    }

    @Nested
    class ExitProgramDialogTest {

        @Test
        void displayExitProgramDialog() throws Exception {
            typeKeysAfterSeconds(List.of(ESCAPE), 2);
            runOnJavaFxThreadAndJoin(JfxDialogUtil::displayExitProgramDialog);
        }
    }


}