#namespace("com.lmmmowi.langame.model.PathNode")

#sql("query")
	SELECT * FROM lg_path_node
	WHERE project = #para(projectId)
	#if(nodeId != null)
  AND `id` = #para(nodeId)
  #end
	#if(parentNodeId != null)
  AND `parent` = #para(parentNodeId)
  #end
  #if(parentNodeIds != null)
  AND `parent` IN  (
	  #for(id:parentNodeIds)
      #(for.index == 0 ? "" : ",") #para(id)
    #end
	)
  #end
  #if(type != null)
  AND `type` = #para(type)
  #end
  #if(matchValue != null)
  AND `name` LIKE #para("%"+matchValue+"%")
  #end
  ORDER BY `type` DESC, `name`
#end

#end
