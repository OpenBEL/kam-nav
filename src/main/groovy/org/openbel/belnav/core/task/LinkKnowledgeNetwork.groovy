package org.openbel.belnav.core.task

import org.cytoscape.application.CyApplicationManager
import org.cytoscape.view.model.CyNetworkView
import org.cytoscape.work.TaskMonitor
import org.cytoscape.work.Tunable
import org.cytoscape.work.util.ListSingleSelection
import org.openbel.ws.api.WsAPI
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import static org.cytoscape.model.CyNetwork.NAME

class LinkKnowledgeNetwork extends BaseTask {

    private static final Logger msg = LoggerFactory.getLogger("CyUserMessages")

    private final CyApplicationManager appMgr
    private final WsAPI wsAPI
    private final CyNetworkView cyNv

    // tunable state
    private String knName
    private ListSingleSelection<String> knNameSelection

    private LinkKnowledgeNetwork(final CyApplicationManager appMgr,
            final WsAPI wsAPI, final CyNetworkView cyNv) {
        this.appMgr = appMgr
        this.wsAPI = wsAPI
        this.cyNv = cyNv
    }

    // Called by cytoscape
    @Tunable(description = "Knowledge network")
    public ListSingleSelection<String> getKnName() {
        knNameSelection = knNameSelection ?:
            new ListSingleSelection<String>(wsAPI.knowledgeNetworks().keySet() as String[])
    }

    // Called by cytoscape
    public void setKnName(ListSingleSelection<String> lsel) {
        this.knName = lsel.selectedValue
    }

    /**
     * {@inheritDoc}
     */
    @Override void doRun(TaskMonitor m) throws Exception {
        def cyN = cyNv.model
        if (!cyN.nodeCount) {
            msg.error("0 nodes in network.")
            return
        }
        def name = cyN.getRow(cyN).get(NAME, String.class)
        m.title = "Link $name (Network) to $knName (Knowledge Network)"

        m.statusMessage = "Loading $knName."
        wsAPI.loadKnowledgeNetwork(knName)

        m.statusMessage = "Resolving nodes to $knName"
        def nodeCount = wsAPI.linkNodes(cyNv.model, knName).count {it}
        m.statusMessage = "Resolving edges to $knName"
        def edgeCount = wsAPI.linkEdges(cyNv.model, knName).count {it}
        msg.info("Linked ${nodeCount} nodes and ${edgeCount} edges.")
    }
}
