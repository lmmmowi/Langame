<resources>
<%
if (items != null){
    for(item in items){
%>
    <string name="${item.key}">${item.content}</string>
<%
    }
}
%>
</resources>