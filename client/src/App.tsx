import logo from "./assets/logo.svg";

function App() {
  return (
    <>
      <header className="flex justify-between w-full max-w-screen-xl h-16 px-4 py-2">
        <div id="header--logo">
          <img src={logo} className="h-full aspect-square" alt="Logo" />
        </div>
        <nav className="flex items-center justify-between w-full"></nav>
      </header>
      <main className="w-full flex-1"></main>
    </>
  );
}

export default App;
