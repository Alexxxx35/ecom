import './App.css';

import UserComponent from './components/UserComponent';
import LoginComponent from "./components/LoginComponent";
import { BrowserRouter, Route } from "react-router-dom";

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <BrowserRouter>
          <Route exact path="/" component={LoginComponent} />
          <Route exact path="/user" component={UserComponent} />
        </BrowserRouter>
      </header>
    </div>
  );
}

export default App;
