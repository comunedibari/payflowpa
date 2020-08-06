package it.tasgroup.iris.web.backoffice.ente.decorator;

public class MarkupHelper {
    public enum StatusLevel {
        INVERSE("label-inverse","testonormale", ""),
        INFO("label-info", "testonormale", "icon-info-sign"),
        SUCCESS("label-success","testonormaleverde", "icon-info-sign"),
        WARNING("label-warning","testonormalegiallo", "icon-exclamation-sign"),
        IMPORTANT("label-important","testonormalerosso", "icon-warning-sign");

        private String cssLevel;      
        private String bkOfCssLevel;
        private String defaultIcon;


        private StatusLevel(String cssLevel, String bkOfCssLevel, String defaultIcon) {
            this.cssLevel = cssLevel;
            this.bkOfCssLevel = bkOfCssLevel;
            this.defaultIcon = defaultIcon;
        }

        public String getCssLevel() {
            return cssLevel;
        }

        public String getDefaultIcon() {
            return defaultIcon;
        }

		public String getBkOfCssLevel() {
			return bkOfCssLevel;
		}

    }

    public enum ButtonLevel {
        PRIMARY("iris-button-primary", "icon-ok-sign"),
        INFO("iris-button-info", "icon-info-sign"),
        SUCCESS("iris-button-success", "icon-ok-sign"),
        DANGER("iris-button-danger", "icon-exclamation-sign"),
        WARNING("iris-button-warning", "icon-warning-sign"),
        INVERSE("iris-button-inverse", "");

        private String cssLevel;
        private String defaultIcon;


        private ButtonLevel(String cssLevel, String defaultIcon) {
            this.cssLevel = cssLevel;
            this.defaultIcon = defaultIcon;
        }

        public String getCssLevel() {
            return cssLevel;
        }


        public String getDefaultIcon() {
            return defaultIcon;
        }

    }

    public enum ButtonDimension {
        SMALL ("iris-btn-small"),
        MEDIUM ("iris-btn-medium"),
        LARGE("iris-btn-large");

        private String value;

        private ButtonDimension(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }


    /**
     * Ritorna un bottone con icona specificata
     *
     * @param link
     * @param iconName
     * @param buttonLevel
     * @param name
     * @return
     */
    public static String createIconButtonLink(String link, String iconName, ButtonLevel buttonLevel, String name) {
        return String.format("<a href=\"%s\" class=\"%s\"><i class=\"%s iris-button-icon\"></i>%s</a>", link, buttonLevel.getCssLevel(), iconName, name);
    }

    public static String createIconButtonLink(String link, String iconName, ButtonLevel buttonLevel, String name, ButtonDimension dimension) {
        return String.format("<a href=\"%s\" class=\"%s %s\"><i class=\"%s iris-button-icon\"></i>%s</a>", link, buttonLevel.getCssLevel(),dimension.getValue(), iconName, name);
    }


    /**
     * Ritorna un bottone con icona di default per il livello speicificato
     *
     * @param link
     * @param buttonLevel
     * @param name
     * @return
     */
    public static String createIconButtonLink(String link, ButtonLevel buttonLevel, String name) {
        return String.format("<a href=\"%s\" class=\"%s\"><i class=\"%s iris-button-icon\"></i>%s</a>", link, buttonLevel.getCssLevel(), buttonLevel.getDefaultIcon(), name);
    }


    /**
     * Bottone senza icona
     *
     * @param link
     * @param buttonLevel
     * @param name
     * @return
     */
    public static String createButtonLink(String link, ButtonLevel buttonLevel, String name) {
        return String.format("<a href=\"%s\" class=\"%s\">%s</a>", link, buttonLevel.getCssLevel(), name);
    }


    /**
     * Badge senza icona
     *
     * @param statusName
     * @param statusLevel
     * @return
     */
    @Deprecated
    public static String createBadge(String statusName, StatusLevel statusLevel) {
        return createStatus(statusName, statusLevel);
    }

    /**
     * Ritorna un badge con icona specificata
     *
     * @param iconName
     * @param statusName
     * @param statusLevel
     * @return
     */
    @Deprecated
    public static String createIconBadge(String iconName, String statusName, StatusLevel statusLevel) {
        return createIconStatus(iconName, statusName, statusLevel);
    }

    /**
     * Ritorna un badge con icona di default per il livello specificato
     *
     * @param statusName
     * @param statusLevel
     * @return
     */
    @Deprecated
    public static String createIconBadge(String statusName, StatusLevel statusLevel) {
        return createIconStatus(statusName, statusLevel);
    }


    /**
     * Badge senza icona
     *
     * @param statusName
     * @param statusLevel
     * @return
     */
    public static String createStatus(String statusName, StatusLevel statusLevel) {
        return String.format("<span style=\"white-space:normal;\" class=\"label %s\">%s</span>", statusLevel.getCssLevel(), statusName);
    }

    /**
     * Ritorna un badge con icona specificata
     *
     * @param iconName
     * @param statusName
     * @param statusLevel
     * @return
     */
    public static String createIconStatus(String iconName, String statusName, StatusLevel statusLevel) {
        return String.format("<span class=\"label %s\"><i class=\"iris-button-icon %s\"></i>%s</span>", statusLevel.getCssLevel(), iconName, statusName);
    }

    /**
     * Ritorna un badge con icona di default per il livello specificato
     *
     * @param statusName
     * @param statusLevel
     * @return
     */
    public static String createIconStatus(String statusName, StatusLevel statusLevel) {
        return String.format("<span class=\"label %s\"><i class=\"iris-button-icon %s\"></i>%s</span>", statusLevel.getCssLevel(), statusLevel.getDefaultIcon(), statusName);
    }
    
    /**
     * Ritorna un badge senza sfondo
     * 
     * @param iconName
     * @param statusName
     * @return
     */
    public static String createTransparentStatus(String iconName, String statusName) {
    	return String.format("<span><i class=\"iris-button-icon %s\"></i> %s</span>", iconName, statusName);
	}


    public enum IconDimension {
        X(""),
        X2("icon-2x"),
        X3("icon-3x"),
        X4("icon-4x");

        String dimension;

        private IconDimension(String dimension) {
            this.dimension = dimension;
        }

        public String getDimension() {
            return dimension;
        }
    }

    public static String createClickableIcon(String link, String iconName, IconDimension iconDimension) {
        return String.format("<a href=\"%s\"><i class=\"clickable %s %s\"></i></a>", link, iconDimension.getDimension(), iconName);
    }
    
    public static String createClickableIcon(String link, String title, String iconName, IconDimension iconDimension) {
        return createClickableIcon(link, title, iconName, iconDimension,false);
    }

    public static String createClickableIcon(String link, String title, String iconName, IconDimension iconDimension, boolean showLabel) {
        return String.format("<a href=\"%s\" title=\"%s\"><i class=\"clickable %s %s\"></i>&nbsp;%s</a>", link, title, iconDimension.getDimension(), iconName, showLabel ? title : "");
    }


    public static String createClickableIconOnlyOneTime(String link, String title, String iconName, IconDimension iconDimension, boolean showLabel) {
    	StringBuffer strb = new StringBuffer();
    	strb.append("<script>").append("\n"); 
    	strb.append("  function clickAndDisable(link) {").append("\n");
    	strb.append("  // disable subsequent clicks").append("\n");
    	strb.append("     link.onclick = function(event) {").append("\n");
    	strb.append("       event.preventDefault();").append("\n");
    	strb.append("   }").append("\n");
    	strb.append("}").append("\n");   
    	strb.append("</script>").append("\n");
    	
        return String.format(strb+"<a href=\"%s\" title=\"%s\" onclick=\"clickAndDisable(this);\"><i class=\"clickable %s %s\"></i>&nbsp;%s</a>", link, title, iconDimension.getDimension(), iconName, showLabel ? title : "");
    }

    public static String createClickableIcon2x(String link, String iconName) {
        return createClickableIcon(link, iconName, IconDimension.X2);
    }

    public static String createClickableIcon(String link, String iconName) {
        return createClickableIcon(link, iconName, IconDimension.X);
    }

    public static void main(String[] args) {
        String icon2x = createClickableIcon2x("http", "icon-file");
        System.out.println("icon2x = " + icon2x);

        String icon = createClickableIcon("http", "icon-file");
        System.out.println("icon = " + icon);
        createClickableIcon("http", "icon-file", IconDimension.X3);

    }
    
    public static String createStatusBK_OF(String statusName, StatusLevel statusLevel) {
       
    	return String.format("<span style=\"\" class=\"%s\">%s</span>", statusLevel.getBkOfCssLevel(), statusName);
    }
		
		

	

}


