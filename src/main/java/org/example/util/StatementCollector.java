package org.example.util;

import org.neo4j.cypherdsl.core.Expression;
import org.neo4j.cypherdsl.core.NodeLabel;
import org.neo4j.cypherdsl.core.Operation;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;


public class StatementCollector implements Function<Expression, Operation> {

    private List<String> statements = new ArrayList<>();



    @Override
    public Operation apply(Expression expression) {
        Operation op = (Operation) expression;
        op.accept(segment -> {
            if (segment instanceof NodeLabel) {
                statements.add(((NodeLabel) segment).getValue());
            }
        });
        return op;
    }


    public List<String> getLabels() {
        return statements;
    }
}
