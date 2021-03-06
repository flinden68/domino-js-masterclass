import React from 'react';
import tempData from '../../../temp-data-store/temp-data';
import Globals from '../../../globals';

import {Tabs, Tab} from 'material-ui/Tabs';
import {Card, CardActions, CardText} from 'material-ui/Card';
import FlatButton from 'material-ui/FlatButton';
import ActionDone from 'material-ui/svg-icons/action/done';
import ContentClear from 'material-ui/svg-icons/content/clear';
import AppBar from 'material-ui/AppBar';
import Checkbox from 'material-ui/Checkbox';
import TextField from 'material-ui/TextField';
import SelectField from 'material-ui/SelectField';
import MenuItem from 'material-ui/MenuItem';
import DatePicker from 'material-ui/DatePicker';

/*
	Component for the to do form
*/

class ToDoForm extends React.Component {
	constructor(props){
		super(props);

		// Load properties from the active entry
		this.state = {
			entry:tempData.toDo.activeEntry,
			priority:tempData.toDo.activeEntry.data.priority,
			assignedTo:tempData.toDo.activeEntry.data.assignedTo,
			storeId:tempData.toDo.activeEntry.data.storeId,
			dueDate:tempData.toDo.activeEntry.data.dueDate,
			isNewDoc:tempData.toDo.activeEntry.custom.isNewDoc
		};
	}

	// onChange event to update the current entry from fields
	onChange(key, value){
		switch(key){
			case "inputStoreId":
				this.state.entry.data.storeId = value;
				this.setState({storeId:value});
				break;			
			case "inputTaskName":
				this.state.entry.data.taskName = value;
				break;
			case "inputDescription":
				this.state.entry.data.description = value;
				break;
			case "inputDueDate":
				this.state.entry.data.dueDate = value;
				this.setState({dueDate:value});
				break;
			case "inputAssignedTo":
				this.state.entry.data.assignedTo = value;
				this.setState({assignedTo:value});
				break;				
			case "inputPriority":
				this.state.entry.data.priority = value;
				this.setState({priority:value});
				break;
		}
	}

	// Emit HTML for the browser
	render(){
		return(
			<form
				className="col-md-12"
				onSubmit={e => {
            		e.preventDefault()
					this.props.onSubmitClick(this.props.state)
        		}}
			>
				<Card>
					<AppBar
						showMenuIconButton={false}
						style={{backgroundColor:this.props.theme.secondaryLight2}}
            			titleStyle={{color:this.props.theme.black}}
						title="To Do Profile"
					/>
					<CardText>
						<div className="row">
							<div className="col-md-6" style={{marginTop:20}}>
								<SelectField
									floatingLabelText="Store"
									floatingLabelFixed={true}
									disabled={!this.state.isNewDoc}
									value={this.state.entry.data.storeId}
									autoWidth={true}
									onChange={(e, index, value) => {
										this.onChange("inputStoreId", value)
									}}
								>
									<MenuItem value={""} primaryText="-Select-" />
									{this.props.storeList.map(entry =>
										<MenuItem key={entry.value} value={entry.value} primaryText={entry.text} />
									)}									
								</SelectField>							
								<TextField
									hintText="Task Name"
									floatingLabelText="Task Name"
									fullWidth={true}
									defaultValue={this.state.entry.data.taskName}
									onChange={(e, value) => {
										this.onChange("inputTaskName", value)
									}}
								/><br />
								<TextField
									hintText="A detailed description for this To Do"
									floatingLabelText="Description"
									fullWidth={true}
									defaultValue={this.state.entry.data.description}
									multiLine={true}
									rowsMax={5}
									onChange={(e, value) => {
										this.onChange("inputDescription", value)
									}}
								/><br /><br />
								<DatePicker
									hintText="Provide a Due Date"
									autoOk={true}
									value={this.state.dueDate}
									onChange={(e, value) => {
										this.onChange("inputDueDate", value)
									}}
								/>
								<SelectField
									floatingLabelText="Responsible Person"
									floatingLabelFixed={true}
									value={this.state.entry.data.assignedTo}
									autoWidth={true}
									onChange={(e, index, value) => {
										this.onChange("inputAssignedTo", value)
									}}
								>
									<MenuItem value={""} primaryText="-Select-" />
									{Globals.userList.map(entry =>
										<MenuItem key={entry.value} value={entry.value} primaryText={entry.text} />
									)}									
								</SelectField><br />
								<SelectField
									floatingLabelText="Priority"
									floatingLabelFixed={true}
									value={this.state.entry.data.priority}
									autoWidth={true}
									onChange={(e, index, value) => {
										this.onChange("inputPriority", value)
									}}
								>
									<MenuItem value={""} primaryText="-Select-" />
									<MenuItem value={"Low"} primaryText="Low" />
									<MenuItem value={"Medium"} primaryText="Medium" />
									<MenuItem value={"High"} primaryText="High" />
								</SelectField>							
							</div>
						</div>
					</CardText>
					<CardActions>
						<div className="row">
              				<div id="divMessages" className="col-xs-12 messagesError"></div>
							<div className="col-xs-12">
								<FlatButton
									type="submit"
									icon={<ActionDone />}
									label="Submit"
									style={{color:this.props.theme.successColor}}
								/>
								<FlatButton
									icon={<ContentClear />}
									label="Cancel"
									secondary={true}
									onTouchTap={e => {
										e.preventDefault()
										this.props.onCancelClick()
									}}
								/>
							</div>
          				</div>
					</CardActions>
				</Card>
			</form>
		)
	}
}

export default ToDoForm;
