#namespace("com.lmmmowi.langame.model.LangEntry")

#sql("query")
	SELECT * FROM lg_lang_entry
	WHERE `node` IN  (
	  #for(id:nodeIds)
      #(for.index == 0 ? "" : ",") #para(id)
    #end
  )
  #if(language != null)
  AND `language` = #para(language)
  #end
#end

#sql("queryUnion")
  SELECT * FROM lg_lang_entry
  WHERE `node` IN  (
	  #for(id:nodeIds)
      #(for.index == 0 ? "" : ",") #para(id)
    #end
  )
  #if(beginTime != null && endTime != null)
  AND create_time BETWEEN #para(beginTime) AND #para(endTime)
  #end
  #if(language != null)
  AND `language`=#para(language)
  #end
  #if(content !=null)
  AND content LIKE #para("%"+content+"%")
  #end
  ORDER BY create_time DESC
#end

#end
