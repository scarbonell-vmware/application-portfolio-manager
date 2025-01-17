import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
const useStyles = makeStyles((theme) => ({
    root: {
        width: '100%',
        height: 400,
        maxWidth: 300,
        backgroundColor: theme.palette.background.paper,
    },
}));


const PropTable = ({ props }) => {

    const classes = useStyles();
    var propList = []

    for (var key in props) {
        if (props.hasOwnProperty(key)) {
            propList.push({ name: key, value: (((typeof props[key]) == 'string') ? props[key] : JSON.stringify(props[key])) })
        }
    }

    return (

        <div>

            <table>
                <th>Name</th><th>Value</th>
                {propList.map((prop, index) =>
                    <tr key={index}>
                        <td>
                            <Typography style={{ 'fontSize': '12px', 'color': 'black', 'fontWeight': 'bold' }}>{prop.name}</Typography>
                        </td>
                        <td>
                            <Typography style={{ 'fontSize': '12px', 'color': 'black' }}>{prop.value}</Typography>
                        </td>
                    </tr>
                )}
            </table>



        </div>

    );
};

export default PropTable;