package org.example;

import org.neo4j.cypherdsl.core.Statement;
import org.neo4j.cypherdsl.parser.CypherParser;
import org.neo4j.cypherdsl.parser.Options;

public class Main {
    public static void main(String[] args) {

        Statement statement = getStatement("MATCH (m:`Person` {id: 100, name: alex}) \n" +
                "CALL apoc.path.spanningTree(p, {minLevel:1,maxLevel:2}) \n" +
                "YIELD path \n" +
                "CALL apoc.path.expandConfig(p, {relationshipFilter:\"FOO\", labelFilter:\"BAR\", uniqueness:\"NODE_GLOBAL\"}) \n" +
                "YIELD path \n" +
                "RETURN path");

        statement.getCatalog().getNodeLabels()
                .stream()
                .forEach(nl -> System.out.println(nl.value()));


    }

    public static Statement getStatement(String cypherQuery) {
        Options options = Options.newOptions().build();
        return CypherParser.parse(cypherQuery, options);
    }



}