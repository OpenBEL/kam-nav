package org.openbel.kamnav.core.task

import static org.openbel.kamnav.common.util.NodeUtil.*
import static org.openbel.kamnav.common.util.EdgeUtil.*
import static java.lang.String.format
import groovy.transform.TupleConstructor
import org.cytoscape.model.CyNode
import org.cytoscape.view.model.CyNetworkView
import org.cytoscape.view.model.View
import org.cytoscape.work.AbstractTask
import org.cytoscape.work.TaskMonitor
import org.openbel.ws.api.WsAPI

@TupleConstructor
class ExpandNode extends AbstractTask {

    final CyNetworkView cyNv
    final View<CyNode> nodeView
    final WsAPI wsAPI

    /**
     * {@inheritDoc}
     */
    @Override
    void run(TaskMonitor monitor) throws Exception {
        def node = toNode.call(cyNv.model, nodeView.model)
        if (!node) {
            monitor.statusMessage = 'The node was not found in the network.'
            cancel()
        }

        monitor.title = format("Expand %s node", node.label)
        monitor.statusMessage = 'Expanding node'

        def edges = wsAPI.adjacentEdges(node)
        def chunk = 1.0d / edges.length
        wsAPI.adjacentEdges(node).each { edge ->
            def s = edge.source
            def t = edge.target
            def cySource = findNode.call(cyNv.model, s.label) ?:
                makeNode.call(cyNv.model, s.id, s.fx.displayValue, s.label)
            def cyTarget =
                findNode.call(cyNv.model, t.label) ?:
                makeNode.call(cyNv.model, t.id, t.fx.displayValue, t.label)
            makeEdge.call(cyNv.model, cySource, cyTarget, edge.id, edge.relationship.displayValue)

            monitor.progress += chunk
        }
    }
}
