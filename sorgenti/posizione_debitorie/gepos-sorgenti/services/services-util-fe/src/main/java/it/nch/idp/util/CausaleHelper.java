package it.nch.idp.util;


import it.tasgroup.addon.internal.AddOnManagerFactory;

// TODO da rimuovere, � solo per gestire il caso bollo auto finch� il plugin non � pronto
public class CausaleHelper {

    public static String getCausale(String causale, String idEnte, String cdTributo, String cdPlugin, String operatore) {
        String causaleHtml = "";
        if (AddOnManagerFactory.exists(cdPlugin)) {
            causaleHtml = PluginViewUtil.getCausale(causale, idEnte, cdTributo, cdPlugin);
        } else {
            causaleHtml = ElencoPendenzeTableDecorator.decorateCausalePendenzaHtml(causale, cdTributo, operatore);
        }

        return causaleHtml;
    }
}
