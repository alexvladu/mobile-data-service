package org.acme.DTO;

import java.util.List;

public class PaginatedResponse<T> {
    private List<T> data;
    private long total;

    public PaginatedResponse(List<T> data, long total) {
        this.data = data;
        this.total = total;
    }

    public List<T> getData() { return data; }
    public long getTotal() { return total; }
}