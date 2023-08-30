interface GameState {
    cells: Cell[];
    initialize: boolean;
    player: number;
    move: boolean;
    build: boolean;
    winner: number | null; // winner maybe null
  }
  
  interface Cell {
    text: string;
  }
  
  export type { GameState, Cell }