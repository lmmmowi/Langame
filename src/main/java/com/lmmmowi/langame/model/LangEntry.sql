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

#end
