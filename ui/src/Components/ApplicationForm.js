
import React from "react";
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';

const ApplicationForm = ({ title, application, onSubmit, onClose }) => {
    const [open, setOpen] = React.useState(false);
  
    const [name, setName] = React.useState(application ? application.name : "");
    const [description, setDescription] = React.useState(application ? application.description : "");
    const [identifier, setIdentifier] = React.useState(application ? application.identifier : "");
    const [businessUnit, setBusinessUnit] = React.useState(application ? application.businessUnit : "");
    const [businessOwner, setBusinessOwner] = React.useState(application ? application.businessOwner : "");
    const [organization, setOrganization] = React.useState(application ? application.organization : "");

    function gatherData () {
       var updatedApplication = {}
       updatedApplication.id = application ? application.id : undefined;
       updatedApplication.name = name;
       updatedApplication.description = description
       updatedApplication.identifier = identifier
       updatedApplication.businessOwner = businessOwner
       updatedApplication.businessUnit = businessUnit
       updatedApplication.organization = organization
       onSubmit(updatedApplication)
    }

    function handleChangeName(event) {
        setName(event.target.value)
    }

    function handleChangeDescription(event) {
        setDescription(event.target.value)
    }

    function handleChangeIdentifier(event) {
        setIdentifier(event.target.value)
    }

    function handleChangeOrganization(event) {
        setOrganization(event.target.value)
    }

    function handleChangeBusinessUnit(event) {
        setBusinessUnit(event.target.value)
    }

    function handleChangeBusinessOwner(event) {
        setBusinessOwner(event.target.value)
    }

    return (

        <div>
  
        <Dialog open={true} onClose={onClose} aria-labelledby="form-dialog-title">
          <DialogTitle id="form-dialog-title">{title}</DialogTitle>
          <DialogContent>
           
            <TextField
              autoFocus
              margin="dense"
              id="name"
              label="Name"
              type="name"
              fullWidth
              value={name}
              onChange={handleChangeName}
            />
             <TextField
              autoFocus
              margin="dense"
              id="identifier"
              label="Identifier"
              type="indentifier"
              fullWidth
              value={identifier}
              onChange={handleChangeIdentifier}
            />
            <TextField
              autoFocus
              margin="dense"
              id="description"
              label="Description"
              type="description"
              fullWidth
              value={description}
              onChange={handleChangeDescription}
            />
            <TextField
              autoFocus
              margin="dense"
              id="businessUnit"
              label="Business Unit"
              type="businessUnit"
              fullWidth
              value={businessUnit}
              onChange={handleChangeBusinessUnit}
            />
             <TextField
              autoFocus
              margin="dense"
              id="businessOwner"
              label="Business Owner"
              type="businessOwner"
              fullWidth
              value={businessOwner}
              onChange={handleChangeBusinessOwner}
            />
             <TextField
              autoFocus
              margin="dense"
              id="organization"
              label="Organization"
              type="organization"
              fullWidth
              value={organization}
              onChange={handleChangeOrganization}
            />
          </DialogContent>
          <DialogActions>
            <Button onClick={onClose} color="primary">
              Cancel
            </Button>
            <Button onClick={gatherData} color="primary">
              {!application ? 'Create' : 'Save'}
            </Button>
          </DialogActions>
        </Dialog>
      </div>

    );
};

export default ApplicationForm;