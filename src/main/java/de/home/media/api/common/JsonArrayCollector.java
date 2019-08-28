package de.home.media.api.common;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import java.util.Collections;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class JsonArrayCollector implements Collector<JsonObject, JsonArrayBuilder, JsonArrayBuilder> {

    @Override
    public Supplier<JsonArrayBuilder> supplier() {
        return Json::createArrayBuilder;
    }

    @Override
    public BiConsumer<JsonArrayBuilder, JsonObject> accumulator() {
        return JsonArrayBuilder::add;
    }

    @Override
    public BinaryOperator<JsonArrayBuilder> combiner() {
        return ((left, right) -> left.add(right));
    }

    @Override
    public Function<JsonArrayBuilder, JsonArrayBuilder> finisher() {
        return Function.identity();
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Collections.singleton(Characteristics.IDENTITY_FINISH);
    }
}
