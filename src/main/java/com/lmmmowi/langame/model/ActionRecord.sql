#namespace("com.lmmmowi.langame.model.ActionRecord")

#sql("query")
	SELECT * FROM lg_action_record
	WHERE project_id=#para(projectId)
#end

#end
