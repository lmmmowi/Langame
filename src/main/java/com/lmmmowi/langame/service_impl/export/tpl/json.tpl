{
<%
if (items != null){
    for(item in items){
%>
    "${item.key}":"${item.content}"${itemLP.last ? "" : ","}
<%
    }
}
%>
}