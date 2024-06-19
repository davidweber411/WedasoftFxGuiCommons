### Description

A library designed to simplify the management of various scenes and dialogs.

### Features

##### Dialog API

- Create complex .fxml-based dialogs with ease, allowing for effortless passing and retrieving of arguments.


- Benefit from predefined dialogs for common tasks, including information, warning, error, confirmation, and input
  dialogs.

##### Scene API

- Easily switch the content of a scene, with optional support for passing and retrieving arguments as needed.

### Requirements

| Technology | Version |
|------------|---------|
| Java       | 17      |
| JavaFX     | 17.0.8  |

### Dependencies to add

##### Maven

    <!-- Maven looks in the central repository by default. -->
    <dependency>
      <groupId>com.wedasoft</groupId>
      <artifactId>wedasoftfxguicommons</artifactId>
      <version>1.0.0</version>
    </dependency>

##### Gradle

    repositories {
      mavenCentral()
    }
    dependencies {
      implementation("com.wedasoft:wedasoftfxguicommons:1.0.0")
    }

### Documentation

#### Create common Dialogs

    JfxDialogUtil.createInformationDialog(String message).showAndWait();

    JfxDialogUtil.createInformationDialog(String message, String messageHeader).showAndWait();

    JfxDialogUtil.createWarningDialog(String message).showAndWait();
    
    JfxDialogUtil.createWarningDialog(String message, String messageHeader).showAndWait();

    JfxDialogUtil.createErrorDialog(String message).showAndWait();
     
    JfxDialogUtil.createErrorDialog(String message, Exception exceptionForStacktrace).showAndWait();

    String inputtedText = JfxDialogUtil.displayInputDialogAndGetResult(String dialogText);

    boolean confirmed = JfxDialogUtil.displayConfirmDialogAndGetResult(String headerText, String contentText);
    
    JfxDialogUtil.displayCloseStageDialog(Stage stageToClose);
    
    JfxDialogUtil.displayExitProgramDialog();

#### Create complex Dialogs

##### Step 1 (optional): Create an init() in the new controller ...

    public void init(String passedParameter){
        // compute the passed parameters
        // do other "constructor things"
    }

##### Step 2: Create and show the dialog

    JfxDialogUtil.createAndShowFxmlDialog(
        /* title */                  "My Dialog title",
        /* dialogIsModal */          true,
        /* dialogIsResizeable */     false,
        /* absoluteFxmlFileUrl */    getClass().getResource("/com/example/app/views/scene1.fxml"),
        /* preferred sceneSize */    new Dimension2D(600, 500),
        /* initMethodOfController */ null / (Consumer<Scene1Controller>) consumer -> consumer.init("myparamter1"),
        /* callbackOnDialogClose */  () -> integerValueChangedByCallback = 52)

#### Switch scene content

##### Step 1 (optional): Create an init() in the new controller ...

    public void init(String passedParameter){
        // compute the passed parameters
        // do other "constructor things"
    }

##### Step 2: Determine the stage ...

    SceneUtil.getStageByActionEvent(ActionEvent event);

    SceneUtil.getStageByChildNode(Node node);

    SceneUtil.getStageByScene(Scene scene);

##### Step 3: Switch the root content of its scene ...

    SceneUtil.switchSceneRoot(
        SceneUtil.getStageByActionEvent(event),
        getClass().getResource("/com/example/app/views/scene1.fxml"),
        null / (Consumer<Scene1Controller>) controller -> controller.init("PassThisToScene1"));

