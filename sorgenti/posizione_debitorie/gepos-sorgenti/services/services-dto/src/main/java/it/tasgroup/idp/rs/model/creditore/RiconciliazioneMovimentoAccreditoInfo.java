package it.tasgroup.idp.rs.model.creditore;

import java.io.Serializable;

/**
 *
 */
public class RiconciliazioneMovimentoAccreditoInfo implements Serializable {
    private Long id;
    private String opAggiornamento;

    public RiconciliazioneMovimentoAccreditoInfo(Long id, String opAggiornamento) {
        this.id = id;
        this.opAggiornamento = opAggiornamento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOpAggiornamento() {
        return opAggiornamento;
    }

    public void setOpAggiornamento(String opAggiornamento) {
        this.opAggiornamento = opAggiornamento;
    }
}
