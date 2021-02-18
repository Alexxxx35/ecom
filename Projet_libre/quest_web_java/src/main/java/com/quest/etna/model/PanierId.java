package com.quest.etna.model;


import java.io.Serializable;
import java.util.Objects;

public class PanierId implements Serializable {
    private static final long serialVersionUID = -9077L;

    
    private int userId;
    private int productId;


    public PanierId(){

    }

    public PanierId(int userId, int productId) {
        this.userId = userId;
        this.productId = productId;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PanierId panierId= (PanierId) o;
        return userId == panierId.userId &&
                productId == panierId.productId;
    }

    public int hashCode() {
        return Objects.hash(userId, productId);
    }


}
