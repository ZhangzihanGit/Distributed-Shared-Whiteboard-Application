package clientPre.clientViewControllers;

public enum FxmlView {

    WELCOME {
        @Override
        String getTitle() {
            return "My WhiteBoard - Client";
        }

        @Override
        String getFxmlFile() {
            return "../clientViews/clientWelcome.fxml";
        }
    }, LOGIN {
        @Override
        String getTitle() {
            return "Login";
        }

        @Override
        String getFxmlFile() {
            return "../clientViews/clientLogin.fxml";
        }
    }, SIGNUP {
        @Override
        String getTitle() {
            return "Sign Up";
        }

        @Override
        String getFxmlFile() {
            return "../clientViews/clientSignup.fxml";
        }
    }, IDENTITY {
        @Override
        String getTitle() {
            return "Choose Your Role";
        }

        @Override
        String getFxmlFile() {
            return "../clientViews/clientIdentity.fxml";
        }
    }, CONFIG {
        @Override
        String getTitle() {
            return "Configuration";
        }

        @Override
        String getFxmlFile() {
            return "../clientViews/clientConfig.fxml";
        }
    }, CANVAS {
        @Override
        String getTitle() {
            return "Canvas";
        }

        @Override
        String getFxmlFile() {
            return "../clientViews/WhiteBoard.fxml";
        }
    }, MQTT {
        @Override
        String getTitle() {
            return "MQTT Configuration";
        }

        @Override
        String getFxmlFile() {
            return "../clientViews/clientMqttConfig.fxml";
        }
    }, WB_LIST {
        @Override
        String getTitle() {
            return "Available Whiteboards";
        }

        @Override
        String getFxmlFile() { return "../clientViews/currentWhiteboards.fxml"; }
    }, CREATE_WB {
        @Override
        String getTitle() {
            return "Create Whiteboard";
        }

        @Override
        String getFxmlFile() { return "../clientViews/createWhiteboard.fxml"; }
    };

    abstract String getTitle();

    abstract String getFxmlFile();
}