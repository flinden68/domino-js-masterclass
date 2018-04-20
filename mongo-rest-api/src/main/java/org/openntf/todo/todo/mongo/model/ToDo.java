/*******************************************************************************
 * Copyright 2018 Paul Withers
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package org.openntf.todo.todo.mongo.model;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.openntf.todo.todo.mongo.exceptions.DataNotAcceptableException;
import org.openntf.todo.todo.mongo.exceptions.DocumentNotFoundException;
import org.openntf.todo.todo.mongo.exceptions.InvalidMetaversalIdException;
import org.openntf.todo.todo.mongo.exceptions.StoreNotFoundException;

/**
 * @author Paul Withers
 * 
 *         ToDo class, mostly auto-generated by Swagger
 *
 */
public class ToDo implements Serializable {

	protected String _rev = null;
	protected String _id = null;

	private String metaversalId = null;

	private String author = null;

	private String taskName = null;

	private String description = null;

	private Date dueDate = null;

	private Priority priority = null;

	private String assignedTo = null;

	private Status status = null;

	/**
	 * Priority for the ToDo, see enum
	 */
	public enum Priority {
		LOW("Low"),

		MEDIUM("Medium"),

		HIGH("High");

		private String value;

		private Priority(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	/**
	 * Current status of the ToDo. Set via workflow actions, defaulting to Active
	 */
	public enum Status {
		ACTIVE("Active"), COMPLETE("Complete"), OVERDUE("Overdue");

		private String value;

		private Status(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	/**
	 * Unique ID across all replicas
	 * 
	 * @return unid
	 **/
	public String getMetaversalId() {
		return metaversalId;
	}

	public void setMetaversalId(String metaversalId) {
		this.metaversalId = metaversalId;
	}

	/**
	 * username of the person who creates the ToDo - set automatically via the API from the currentUser passed across
	 * 
	 * @return author
	 **/
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public ToDo taskName(String taskName) {
		this.taskName = taskName;
		return this;
	}

	/**
	 * Brief description of task
	 * 
	 * @return taskName
	 **/
	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public ToDo description(String description) {
		this.description = description;
		return this;
	}

	/**
	 * Detailed description of the task
	 * 
	 * @return description
	 **/
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ToDo dueDate(Date dueDate) {
		this.dueDate = dueDate;
		return this;
	}

	/**
	 * Date when the task is due in RFC 3339 section 5.6 format 2018-03-19
	 * 
	 * @return dueDate
	 **/
	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public ToDo priority(Priority priority) {
		this.priority = priority;
		return this;
	}

	/**
	 * Priority for the ToDo, see enum
	 * 
	 * @return priority
	 **/
	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public ToDo assignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
		return this;
	}

	/**
	 * Name of the person the ToDo is assigned to. If not set, current user will be used
	 * 
	 * @return assignedTo
	 **/
	public String getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}

	/**
	 * Current status of the ToDo. Set via workflow actions, defaulting to Active
	 * 
	 * @return status
	 **/
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getRev() {
		return _rev;
	}

	public void setRev(String rev) {
		this._rev = rev;
	}

	public String getId() {
		return _id;
	}

	public void setId(String id) {
		this._id = id;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		ToDo toDo = (ToDo) o;
		return Objects.equals(this.metaversalId, toDo.metaversalId) && Objects.equals(this.author, toDo.author)
				&& Objects.equals(this.taskName, toDo.taskName)
				&& Objects.equals(this.description, toDo.description) && Objects.equals(this.dueDate, toDo.dueDate)
				&& Objects.equals(this.priority, toDo.priority) && Objects.equals(this.assignedTo, toDo.assignedTo)
				&& Objects.equals(this.status, toDo.status);
	}

	@Override
	public int hashCode() {
		return Objects.hash(metaversalId, author, taskName, description, dueDate, priority, assignedTo,
				status);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class ToDo {\n");

		sb.append("    unid: ").append(toIndentedString(metaversalId)).append("\n");
		sb.append("    author: ").append(toIndentedString(author)).append("\n");
		sb.append("    taskName: ").append(toIndentedString(taskName)).append("\n");
		sb.append("    description: ").append(toIndentedString(description)).append("\n");
		sb.append("    dueDate: ").append(toIndentedString(dueDate)).append("\n");
		sb.append("    priority: ").append(toIndentedString(priority)).append("\n");
		sb.append("    assignedTo: ").append(toIndentedString(assignedTo)).append("\n");
		sb.append("    status: ").append(toIndentedString(status)).append("\n");
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Convert the given object to string with each line indented by 4 spaces (except the first line).
	 */
	private String toIndentedString(java.lang.Object o) {
		if (o == null) {
			return "null";
		}
		return o.toString().replace("\n", "\n    ");
	}

	public boolean validateForUpdate() throws DataNotAcceptableException {
		ArrayList<String> missing = new ArrayList<String>();
		if (StringUtils.isEmpty(getTaskName())) {
			missing.add("taskName");
		}
		if (StringUtils.isEmpty(getDescription())) {
			missing.add("description");
		}
		if (null == getDueDate()) {
			missing.add("dueDate");
		}
		if (null == getPriority()) {
			missing.add("priority");
		}
		if (!missing.isEmpty()) {
			throw new DataNotAcceptableException(
					"The following properties must be included: " + StringUtils.join(missing, ","));
		}
		checkOverdue();
		return true;
	}

	public boolean checkOverdue() {
		LocalDate dt1 = LocalDate.now();
		LocalDate dt2 = getDueDate().toLocalDate();
		if (dt1.isAfter(dt2)) {
			setStatus(Status.OVERDUE);
			return true;
		}
		return false;
	}

	public boolean serializeToStore(Store store) {
		return false;
	}

	public ToDo compareAndUpdateFromPrevious()
			throws StoreNotFoundException, DocumentNotFoundException, InvalidMetaversalIdException {
		ToDo oldTodo = null; //ToDoStoreFactory.getInstance().getToDoFromMetaversalId(getMetaversalId());
		setMetaversalId(oldTodo.getMetaversalId());
		setAuthor(oldTodo.getAuthor());
		if (StringUtils.isEmpty(getTaskName())) {
			setTaskName(oldTodo.getTaskName());
		}
		if (StringUtils.isEmpty(getDescription())) {
			setDescription(oldTodo.getDescription());
		}
		if (null == getPriority()) {
			setPriority(oldTodo.getPriority());
		}
		setStatus(oldTodo.getStatus());
		if (StringUtils.isEmpty(getAssignedTo())) {
			setAssignedTo(oldTodo.getAssignedTo());
		} else {
			//setAssignedTo(Utils.getAsUsername(getAssignedTo()));
		}
		if (null == getDueDate()) {
			setDueDate(oldTodo.getDueDate());
		}
		checkOverdue();
		return this;
	}

}