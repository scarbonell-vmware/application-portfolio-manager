
import React from "react";
import Dialog from "@material-ui/core/Dialog";
import DialogContentText from "@material-ui/core/DialogContentText";
import DialogTitle from "@material-ui/core/DialogTitle";
import DialogContent from "@material-ui/core/DialogContent";
import CloseIcon from "@material-ui/icons/Close";
import Typography from "@material-ui/core/Typography";
import IconButton from "@material-ui/core/IconButton";
import Grid from "@material-ui/core/Grid";

const AboutDialog = ({ onClose, version }) => {


    return (

        <Dialog open={true} >
            <DialogTitle>
                <Grid container justify="space-between" alignItems="center">
                    <Typography variant="div">About Application Portfolio Manager {version.name} {version.mode =='lite' ? "(Lite)" : ""}</Typography>
                    <IconButton onClick={() => onClose(false)}>
                        <CloseIcon />
                    </IconButton>
                </Grid></DialogTitle>
            <DialogContent>
                <DialogContentText>
                <Typography variant="div" style={{fontSize: '15px'}}>Application Portfolio Manager is a proprietary tool for use only by authorized personnel.</Typography>
                <br></br>
<Typography variant="div" style={{fontSize: '15px'}}>
Copying, reverse engineering, and/or distribution is strictly forbidden.</Typography>
                </DialogContentText>
            </DialogContent>
        </Dialog>

    );
};

export default AboutDialog;