package wbServerPre.wbServerViewControllers;

public enum WbServerFxmlView {

    WELCOME {
        @Override
        String getTitle() {
            return "My WhiteBoard - Whiteboard Server";
        }

        @Override
        String getFxmlFile() {
            return "../wbServerViews/wbServerWelcome.fxml";
        }
    }, CONFIG {
        @Override
        String getTitle() {
            return "Whiteboard Server Configuration";
        }

        @Override
        String getFxmlFile() {
            return "../wbServerViews/wbServerConfig.fxml";
        }
    }, CONFIG_DB {
        @Override
        String getTitle() {
            return "Database Server Configuration";
        }

        @Override
        String getFxmlFile() {
            return "../wbServerViews/wbServerDbConfig.fxml";
        }
    }
    , MQTT {
        @Override
        String getTitle() {
            return "MQTT Configuration";
        }

        @Override
        String getFxmlFile() {
            return "../wbServerViews/wbServerMqttConfig.fxml";
        }
    }, WB_LIST {
        @Override
        String getTitle() {
            return "Available Whiteboards";
        }

        @Override
        String getFxmlFile() { return "../wbServerViews/wbCurrentWbs.fxml"; }
    }, MONITOR {
        @Override
        String getTitle() {
            return "Monitor Whiteboard";
        }

        @Override
        String getFxmlFile() { return "../wbServerViews/wbServerMonitor.fxml"; }
    };

    abstract String getTitle();

    abstract String getFxmlFile();
}
