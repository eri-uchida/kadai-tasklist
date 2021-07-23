<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<label>タスク<br />
<input type="text"  name="content" value="${task.content}"/>
</label>
<br /><br />
<input type="hidden" name="_token" value="${_token}" />
<button type="submit">登録</button>