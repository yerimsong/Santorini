import React from 'react';
import './App.css'; // import the css file to enable your styles.
import { GameState, Cell } from './game';
import BoardCell from './Cell';

/**
 * Define the type of the props field for a React component
 */
interface Props { }

/**
 * Using generics to specify the type of props and state.
 * props and state is a special field in a React component.
 * React will keep track of the value of props and state.
 * Any time there's a change to their values, React will
 * automatically update (not fully re-render) the HTML needed.
 * 
 * props and state are similar in the sense that they manage
 * the data of this component. A change to their values will
 * cause the view (HTML) to change accordingly.
 * 
 * Usually, props is passed and changed by the parent component;
 * state is the internal value of the component and managed by
 * the component itself.
 */
class App extends React.Component<Props, GameState> {
  private initialized: boolean = false;

  /**
   * @param props has type Props
   */
  constructor(props: Props) {
    super(props)
    /**
     * state has type GameState as specified in the class inheritance.
     */
    this.state = { cells: [], 
                   initialize: false,
                   player: 1, 
                   move: false, 
                   build: false,
                   winner: null }
  }

  /**
   * Use arrow function, i.e., () => {} to create an async function,
   * otherwise, 'this' would become undefined in runtime. This is
   * just an issue of Javascript.
   */
  newGame = async () => {
    const response = await fetch('/newgame');
    const json = await response.json();
    console.log(json);
    this.setState({ cells: json['cells'], initialize: false, move: false, build: false, player: 1, winner: null});
  }

  async initialize(x1: number, y1: number, x2: number, y2: number, x3: number, y3: number, x4: number, y4: number, gc1: String, gc2: String) {
      // prevent the default behavior on clicking a link; otherwise, it will jump to a new page.
      const response = await fetch(`/initialize?x1=${x1}&y1=${y1}&x2=${x2}&y2=${y2}&x3=${x3}&y3=${y3}&x4=${x4}&y4=${y4}&gc1=${gc1}&gc2=${gc2}`)
      const json = await response.json();
      if (json.haserror) {
        alert("Invalid initialization inputs");
        this.newGame();
      }
      else {
        this.setState({initialize: true});
        this.setState(json);
      }
  }

  /**
   * play will generate an anonymous function that the component
   * can bind with.
   * @param x 
   * @param y 
   * @returns 
   */
  play(x: number, y: number): React.MouseEventHandler {
    // select worker
    if (!this.state.move && !this.state.build) {
      return async (e) => {
        // prevent the default behavior on clicking a link; otherwise, it will jump to a new page.
        e.preventDefault();
        const response = await fetch(`/selectworker?x=${x}&y=${y}`)
        const json = await response.json();
        if (json.haserror) {
          alert("Invalid worker selected");
        }
        else {
          this.setState({move: true})
          this.setState(json);
        }
      }
    }
    // move
    else if (this.state.move) {
      return async (e) => {
        e.preventDefault();
        const response = await fetch(`/move?x=${x}&y=${y}`)
        const json = await response.json();
        if (json.haserror) {
          alert("Invalid move");
        }
        else {
          this.setState({move: false, build: true})
          this.setState(json);
        }
      }
    }
    // build
    else if (this.state.build) {
      return async (e) => {
        // prevent the default behavior on clicking a link; otherwise, it will jump to a new page.
        e.preventDefault();
        const response = await fetch(`/build?x=${x}&y=${y}`)
        const json = await response.json();
        console.log(json);
        if (json.haserror) {
          alert("Invalid build");
        }
        else {
          if (json.continueBuild) {
            this.setState({build: true})
            this.setState(json);
          }
          else {
            this.setState({build: false, player: this.state.player === 1 ? 2 : 1})
            this.setState(json);
          }
        }
        
      }
    }
    else {
      return async (e) => {
        e.preventDefault();
        const response = await fetch(`/play?x=${x}&y=${y}`)
        const json = await response.json();
        this.setState(json);
      }
    }
  }

  undo = async () => {
    const response = await fetch('/undo');
    const json = await response.json();
    this.setState(json);
  }

  createCell(cell: Cell, index: number): React.ReactNode {
    //if (cell.playable)
      /**
       * key is used for React when given a list of items. It
       * helps React to keep track of the list items and decide
       * which list item need to be updated.
       * @see https://reactjs.org/docs/lists-and-keys.html#keys
       */
    return (
      <div key={index}>
        {/* <a href='/' onClick={this.play(cell.x, cell.y)}> */}
        <a href='/' onClick={this.play(Math.floor(index/5), index%5)}>
          <BoardCell cell={cell}></BoardCell>
        </a>
      </div>
    )
  }

  createInstruction(): React.ReactNode {
    if (this.state.winner !== null) {
      return `Player ${this.state.winner} wins!`
    }
    else if (!this.state.initialize) {
      return 'Initialize worker locations and player god cards.'
    }
    else {
      if (!this.state.move && !this.state.build) {
        return `Click one of Player ${this.state.player}'s workers.`
      }
      else if (this.state.move) {
        return `Click a tile for Player ${this.state.player}'s selected worker to move into.`
      }
      else if (this.state.build) {
        return `Click a tile for Player ${this.state.player}'s selected worker to build in.`
      }
      else {
        return `Invalid state. Should not reach here.`
      }
    }
  }

  /**
   * This function will call after the HTML is rendered.
   * We update the initial state by creating a new game.
   * @see https://reactjs.org/docs/react-component.html#componentdidmount
   */
  componentDidMount(): void {
    /**
     * setState in DidMount() will cause it to render twice which may cause
     * this function to be invoked twice. Use initialized to avoid that.
     */
    if (!this.initialized) {
      this.newGame();
      this.initialized = true;
    }
  }

  /**
   * The only method you must define in a React.Component subclass.
   * @returns the React element via JSX.
   * @see https://reactjs.org/docs/react-component.html
   */
  render(): React.ReactNode {
    /**
     * We use JSX to define the template. An advantage of JSX is that you
     * can treat HTML elements as code.
     * @see https://reactjs.org/docs/introducing-jsx.html
     */

    interface FormElements extends HTMLFormControlsCollection {
      P1x1: HTMLInputElement
      P1y1: HTMLInputElement
      P1x2: HTMLInputElement
      P1y2: HTMLInputElement
      P2x1: HTMLInputElement
      P2y1: HTMLInputElement
      P2x2: HTMLInputElement
      P2y2: HTMLInputElement
      gc1: HTMLInputElement
      gc2: HTMLInputElement
    }
    interface YerimFormElement extends HTMLFormElement {
      readonly elements: FormElements
    }

    return (
      <div>
        <div id="instructions">{this.createInstruction()}</div>
        <div id="initialize">
          {!this.state.initialize &&
          <form onSubmit={(event: React.FormEvent<YerimFormElement>) => {
                event.preventDefault()
                let elements = event.currentTarget.elements
                this.initialize(
                  parseInt(elements.P1x1.value), 
                  parseInt(elements.P1y1.value), 
                  parseInt(elements.P1x2.value), 
                  parseInt(elements.P1y2.value), 
                  parseInt(elements.P2x1.value), 
                  parseInt(elements.P2y1.value), 
                  parseInt(elements.P2x2.value), 
                  parseInt(elements.P2y2.value),
                  elements.gc1.value,
                  elements.gc2.value)
          }}>
            <p>
              Enter Player 1 initialization info (coordinates must be bewtween 0-4):
            </p>
            <label>
              Worker 1 x-coordinate:
              <input type="number" id="P1x1" name="P1x1"/>
              Worker 1 y-coordinate:
              <input type="number" id="P1y1" name="P1y1" />
              Worker 2 x-coordinate:
              <input type="number" id="P1x2" name="P1x2" />
              Worker 2 y-coordinate:
              <input type="number" id="P1y2" name="P1y2" />
              {/* Player 1 god card:
              <input type="text" id="gc1" name="gc1" /> */}
            </label>
            Choose a god card:
            <select id="gc1">
              <option value="none">no god card</option>
              <option value="demeter">Demeter</option>
              <option value="minotaur">Minotaur</option>
              <option value="pan">Pan</option>
            </select>
            <p>
              Enter Player 2 initialization info (coordinates must be bewtween 0-4):
            </p>
            <label>
              Worker 1 x-coordinate:
              <input type="number" id="P2x1" name="P2x1" />
              Worker 1 y-coordinate:
              <input type="number" id="P2y1" name="P2y1" />
              Worker 2 x-coordinate:
              <input type="number" id="P2x2" name="P2x2" />
              Worker 2 y-coordinate:
              <input type="number" id="P2y2" name="P2y2" />
              {/* Player 2 god card:
              <input type="text" id="gc2" name="gc2" /> */}
            </label>
            Choose a god card:
            <select id="gc2">
              <option value="none">no god card</option>
              <option value="demeter">Demeter</option>
              <option value="minotaur">Minotaur</option>
              <option value="pan">Pan</option>
            </select>
            <input type="submit" value="Submit"/>
          </form>}
        </div>
        <div id="board">
          {this.state.cells.map((cell, i) => this.createCell(cell, i))}
        </div>
       <div id="bottombar">
          <button onClick={/* get the function, not call the function */this.newGame}>New Game</button>
        </div>
      </div>
    );
  }
}

export default App;
