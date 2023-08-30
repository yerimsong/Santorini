import React from 'react';
import { Cell } from './game';

interface Props {
  cell: Cell
}

class BoardCell extends React.Component<Props> {
  render(): React.ReactNode {
    const player1 = false;
    return (
      <div className={`cell ${player1}`}>{this.props.cell.text}</div>
    )
  }
}

export default BoardCell;