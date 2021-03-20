package se.dandel.gameon.adapter.out.rest.api1;

import org.apache.commons.lang3.builder.RecursiveToStringStyle;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class EnvelopeDTO<Q extends AbstractQueryDTO, D> {
    private Q query;

    private D data;

    public Q getQuery() {
        return query;
    }

    public void setQuery(Q queryDTO) {
        this.query = queryDTO;
    }

    public D getData() {
        return data;
    }

    public void setData(D data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, RecursiveToStringStyle.SHORT_PREFIX_STYLE);
    }
}
