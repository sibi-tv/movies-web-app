export default function Hero() {
  return (
    <section
      className="relative h-[60vh] flex items-center justify-center bg-cover bg-center text-white"
      style={{
        backgroundImage: "url('https://image.tmdb.org/t/p/original/t/p/w1280/nGxUxi3PfXDRm7Vg95VBNgNM8yc.jpg')",
      }}
    >

      <div className="absolute inset-0 bg-black/50" />

      <div className="relative z-10 mx-auto max-w-4xl px-6 text-center">
        <h1 className="text-4xl md:text-5xl font-bold">Trending Movies</h1>
        <p className="mt-3 text-neutral-300 font-bold text-lg">
          Have you seen these trending movies? Pick your favorites!
        </p>
      </div>
    </section>
  );
}