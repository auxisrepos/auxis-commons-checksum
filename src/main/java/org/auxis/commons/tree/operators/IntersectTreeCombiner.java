package org.auxis.commons.tree.operators;

import org.auxis.commons.tree.Tree;
import org.auxis.commons.tree.TreeBuilder;
import org.auxis.commons.tree.TreeCombiner;
import org.auxis.commons.tree.TreeIndex;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class IntersectTreeCombiner implements TreeCombiner
{
    private final Provider<TreeBuilder> treeBuilderProvider;

    @Inject
    public IntersectTreeCombiner( Provider<TreeBuilder> builder )
    {
        treeBuilderProvider = builder;
    }

    @Override public Tree combine( Tree left, Tree right )
    {
        TreeBuilder builder = treeBuilderProvider.get();

        return builder.seal();
    }

    /**
     * Structure-aware identity.
     *
     * @param collector
     * @param left
     * @param right
     */
    static void identBySelector( TreeBuilder collector, TreeIndex left, TreeIndex right )
    {

    }

    /**
     * Hash based identity.
     * Copies all trees from left that have an equivalent in right.
     * <p/>
     * By doing this from both sides, one could construct a nice "move-" tree.
     *
     * @param collector
     * @param left
     * @param right
     */
    static void identLeftHash( TreeBuilder collector, TreeIndex left, TreeIndex right )
    {
        identLeftHash( collector, left, right, buildDeep( right ) );
    }

    static void identLeftHash( TreeBuilder collector, TreeIndex left, TreeIndex right, Map<String, Tree> index )
    {

        //if (index.containsKey(left.))
        if ( !left.fingerprint().equals( right.fingerprint() ) )
        {
            // dig deeper

        }
        else
        {
            // fast forward, copy whole branch.
            collector.branch( left );
        }
    }

    private static Map<String, Tree> buildDeep( Tree tree )
    {
        Map<String, Tree> index = new HashMap<String, Tree>();
        buildDeep( tree, index );
        return index;
    }

    private static void buildDeep( Tree tree, Map<String, Tree> index )
    {
        index.put( tree.fingerprint(), tree );
        for ( Tree sub : tree.branches() )
        {
            buildDeep( sub, index );
        }
    }

}
