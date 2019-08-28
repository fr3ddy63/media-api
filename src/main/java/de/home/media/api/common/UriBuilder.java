package de.home.media.api.common;

import javax.ws.rs.core.Link;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UriBuilder {

    public static List<Link> links(UriInfo info, Parameter param, Long count) {

        List<Link> links = new ArrayList<>();

        Integer page = param.getPage();
        Integer rpp = param.getRpp();

        Long lMaxPage =count / rpp.longValue();
        Integer maxPage = lMaxPage.intValue();

        Map<String, Integer> params = new HashMap<>();
        if (page > 1) params.put("first", 0);
        if (page > 0) params.put("prev", page - 1);
        if (page < maxPage) params.put("next", page + 1);
        if (page < (maxPage - 1)) params.put("last", count.intValue() / rpp);

        params.forEach((key, value) -> {
            links.add(Link.fromUriBuilder(
                    info.getRequestUriBuilder()
                            .replaceQueryParam("page", value)
                            .replaceQueryParam("rpp", rpp))
                    .rel(key)
                    .build());
        });

        return links;
    }
}
