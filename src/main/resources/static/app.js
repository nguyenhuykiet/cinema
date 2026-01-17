const $ = (id) => document.getElementById(id);

const state = {
  movies: [],
  movie: null,
  shows: [],
  show: null,
  seats: [],
  creds: loadCreds(),
  profile: null,
  role: null
};

function loadCreds() {
  try { return JSON.parse(localStorage.getItem("cinemaCreds") || "null"); }
  catch { return null; }
}
function saveCreds(email, password) {
  state.creds = { email, password };
  localStorage.setItem("cinemaCreds", JSON.stringify(state.creds));
}
function clearCreds() {
  state.creds = null;
  state.profile = null;
  state.role = null;
  localStorage.removeItem("cinemaCreds");
  syncAuthUI();
}
function authHeader() {
  if (!state.creds) return {};
  const token = btoa(`${state.creds.email}:${state.creds.password}`);
  return { Authorization: `Basic ${token}` };
}

function toast(msg) {
  const el = $("toast");
  el.textContent = msg;
  el.classList.remove("hidden");
  clearTimeout(el._t);
  el._t = setTimeout(() => el.classList.add("hidden"), 2400);
}

async function api(path, opts = {}) {
  const headers = { ...(opts.headers || {}) };
  const withAuth = opts.withAuth || false;
  if (withAuth) Object.assign(headers, authHeader());
  if (opts.json) headers["Content-Type"] = "application/json";
  const res = await fetch(path, { ...opts, headers });
  if (!res.ok) {
    const text = await res.text().catch(() => "");
    throw new Error(text || `${res.status} ${res.statusText}`);
  }
  const ct = res.headers.get("content-type") || "";
  if (ct.includes("application/json")) return res.json();
  return res.text();
}

async function apiMultipart(path, formData, method = "POST") {
  const headers = { ...authHeader() };
  const res = await fetch(path, { method, headers, body: formData });
  if (!res.ok) {
    const text = await res.text().catch(() => "");
    throw new Error(text || `${res.status} ${res.statusText}`);
  }
  const ct = res.headers.get("content-type") || "";
  if (ct.includes("application/json")) return res.json();
  return res.text();
}

function showView(id) {
  for (const el of document.querySelectorAll(".view")) el.classList.add("hidden");
  $(id).classList.remove("hidden");
}

function escapeHtml(s) {
  return String(s).replace(/[&<>"']/g, (c) => ({
    "&": "&amp;",
    "<": "&lt;",
    ">": "&gt;",
    "\"": "&quot;",
    "'": "&#39;"
  }[c]));
}

function renderMovies(list) {
  const wrap = $("movies");
  wrap.innerHTML = "";
  $("moviesEmpty").classList.toggle("hidden", list.length !== 0);

  for (const m of list) {
    const card = document.createElement("div");
    card.className = "card movieCard";

    const img = document.createElement("img");
    img.className = "poster";
    img.alt = m.title || "Poster";
    img.src = m.posterUrl || "";
    img.onerror = () => { img.style.display = "none"; };

    const t = document.createElement("div");
    t.className = "movieTitle";
    t.textContent = m.title || "(untitled)";

    const btn = document.createElement("button");
    btn.textContent = "View";
    btn.onclick = () => loadMovie(m.movieID);

    card.appendChild(img);
    card.appendChild(t);
    card.appendChild(btn);
    wrap.appendChild(card);
  }
}

function renderMovieDetail(m) {
  const box = $("movieDetail");
  const poster = m.posterUrl ? `<img class="poster" style="max-width:220px" src="${m.posterUrl}" onerror="this.style.display='none'">` : "";
  const synopsis = m.synopsis ? `<div class="muted" style="margin-top:10px;white-space:pre-wrap">${escapeHtml(m.synopsis)}</div>` : "";
  box.innerHTML = `
    <div style="display:flex;gap:14px;flex-wrap:wrap">
      ${poster}
      <div style="min-width:240px;flex:1">
        <div style="font-size:20px;font-weight:750">${escapeHtml(m.title || "")}</div>
        <div style="margin-top:8px;display:flex;gap:10px;flex-wrap:wrap">
          ${m.ageRating ? `<span class="badge">Age ${escapeHtml(m.ageRating)}</span>` : ""}
          ${m.duration ? `<span class="badge">${m.duration} min</span>` : ""}
          ${m.openingDay ? `<span class="badge">Open ${escapeHtml(m.openingDay)}</span>` : ""}
        </div>
        ${m.directors ? `<div class="muted" style="margin-top:10px">Directors: ${escapeHtml(m.directors)}</div>` : ""}
        ${m.casts ? `<div class="muted">Casts: ${escapeHtml(m.casts)}</div>` : ""}
        ${synopsis}
      </div>
    </div>
  `;
}

function renderShows(shows) {
  const wrap = $("shows");
  wrap.innerHTML = "";
  $("showsEmpty").classList.toggle("hidden", shows.length !== 0);

  for (const s of shows) {
    const row = document.createElement("div");
    row.className = "card";
    const when = s.showTime ? new Date(s.showTime).toLocaleString() : "";
    row.innerHTML = `
      <div style="display:flex;align-items:center;justify-content:space-between;gap:10px;flex-wrap:wrap">
        <div>
          <div style="font-weight:650">${escapeHtml(s.auditoriumName || "")}</div>
          <div class="muted">${escapeHtml(when)}</div>
        </div>
        <div style="display:flex;align-items:center;gap:10px">
          <span class="badge">${s.price ?? ""}</span>
          <button>Select seats</button>
        </div>
      </div>
    `;
    row.querySelector("button").onclick = () => loadSeats(s);
    wrap.appendChild(row);
  }
}

function renderSeats(seats) {
  const wrap = $("seats");
  wrap.innerHTML = "";
  for (const seat of seats) {
    const b = document.createElement("button");
    b.className = "seatBtn";
    b.textContent = seat.name;
    if (seat.booked) {
      b.disabled = true;
      b.classList.add("booked");
    }
    b.onclick = () => bookSeat(seat);
    wrap.appendChild(b);
  }
}

function renderTickets(tickets) {
  const wrap = $("tickets");
  wrap.innerHTML = "";
  $("ticketsEmpty").classList.toggle("hidden", tickets.length !== 0);

  for (const t of tickets) {
    const row = document.createElement("div");
    row.className = "card";
    const when = t.showTime ? new Date(t.showTime).toLocaleString() : "";
    row.innerHTML = `
      <div style="display:flex;align-items:center;justify-content:space-between;gap:10px;flex-wrap:wrap">
        <div>
          <div style="font-weight:650">${escapeHtml(t.title || "")}</div>
          <div class="muted">${escapeHtml(when)} · Seat ${escapeHtml(t.seatName || "")}</div>
        </div>
        <button class="link">Cancel</button>
      </div>
    `;
    row.querySelector("button").onclick = () => cancelTicket(t.ticketId);
    wrap.appendChild(row);
  }
}

async function refreshProfile() {
  if (!state.creds) {
    state.profile = null;
    state.role = null;
    syncAuthUI();
    return;
  }
  try {
    const p = await api("/user/profile", { withAuth: true });
    state.profile = p;
    state.role = (p && p.role) ? String(p.role).toUpperCase() : null;
    syncAuthUI();
  } catch {
    clearCreds();
  }
}

function syncAuthUI() {
  const authed = !!state.creds;
  const role = state.role;
  $("btnLogout").classList.toggle("hidden", !authed);
  $("btnMyTickets").classList.toggle("hidden", !(authed && role === "GUEST"));
  $("btnAdmin").classList.toggle("hidden", !(authed && role === "ADMIN"));

  const userInfo = $("userInfo");
  if (!authed) {
    userInfo.textContent = "";
    return;
  }
  const email = state.creds?.email || "";
  userInfo.textContent = role ? `${email} · ${role}` : email;
}

async function loadAllMovies() {
  try {
    const list = await api("/movie/all");
    state.movies = list;
    renderMovies(list);
    showView("viewMovies");
  } catch (e) {
    toast(String(e.message || e));
  }
}

async function searchMovies() {
  const q = $("q").value.trim();
  if (!q) return loadAllMovies();
  try {
    const list = await api(`/movie/search/${encodeURIComponent(q)}`);
    state.movies = list;
    renderMovies(list);
    showView("viewMovies");
  } catch (e) {
    toast(String(e.message || e));
  }
}

async function loadMovie(id) {
  try {
    const m = await api(`/movie/${encodeURIComponent(id)}`);
    state.movie = m;
    renderMovieDetail(m);
    const shows = await api(`/movie/${encodeURIComponent(id)}/show`);
    state.shows = shows;
    renderShows(shows);
    showView("viewMovie");
  } catch (e) {
    toast(String(e.message || e));
  }
}

async function loadSeats(show) {
  state.show = show;
  const head = $("seatHeader");
  const when = show.showTime ? new Date(show.showTime).toLocaleString() : "";
  head.innerHTML = `
    <div style="display:flex;align-items:center;justify-content:space-between;gap:10px;flex-wrap:wrap">
      <div>
        <div style="font-weight:700">${escapeHtml(show.movieTitle || "")}</div>
        <div class="muted">${escapeHtml(show.auditoriumName || "")} · ${escapeHtml(when)}</div>
      </div>
      <div class="badge">Show ${show.showId}</div>
    </div>
  `;
  try {
    const seats = await api(`/show/${encodeURIComponent(show.showId)}/seat`);
    state.seats = seats;
    renderSeats(seats);
    showView("viewSeats");
  } catch (e) {
    toast(String(e.message || e));
  }
}

async function bookSeat(seat) {
  if (!state.creds) {
    toast("Login required");
    $("dlgAuth").showModal();
    return;
  }
  if (state.role !== "GUEST") {
    toast("Guest account required for booking");
    return;
  }
  try {
    await api("/ticket/book", {
      method: "POST",
      withAuth: true,
      json: true,
      body: JSON.stringify({ showId: state.show.showId, seatId: seat.seatId })
    });
    toast("Booked");
    await loadSeats(state.show);
  } catch (e) {
    toast(String(e.message || e));
  }
}

async function loadMyTickets() {
  if (!state.creds) {
    toast("Login required");
    $("dlgAuth").showModal();
    return;
  }
  if (state.role !== "GUEST") {
    toast("Guest account required");
    return;
  }
  try {
    const list = await api("/ticket/my", { withAuth: true });
    renderTickets(list);
    showView("viewMyTickets");
  } catch (e) {
    toast(String(e.message || e));
  }
}

async function cancelTicket(ticketId) {
  try {
    await api(`/ticket/${encodeURIComponent(ticketId)}`, { method: "DELETE", withAuth: true });
    toast("Cancelled");
    await loadMyTickets();
  } catch (e) {
    toast(String(e.message || e));
  }
}

async function doRegister() {
  $("regMsg").textContent = "";
  const payload = {
    email: $("regEmail").value.trim(),
    password: $("regPassword").value,
    fullName: $("regFullName").value.trim(),
    dateOfBirth: $("regDob").value || null,
    tel: $("regTel").value.trim()
  };
  try {
    await api("/user/register", { method: "POST", json: true, body: JSON.stringify(payload) });
    $("regMsg").textContent = "Account created. Login now.";
    $("tabLogin").click();
  } catch (e) {
    $("regMsg").textContent = String(e.message || e);
  }
}

async function doLogin() {
  $("loginMsg").textContent = "";
  const email = $("loginEmail").value.trim();
  const password = $("loginPassword").value;
  try {
    saveCreds(email, password);
    await refreshProfile();
    if (!state.creds || !state.role) throw new Error("Login failed");
    $("loginMsg").textContent = `Logged in as ${state.role}`;
    $("dlgAuth").close();
  } catch {
    clearCreds();
    $("loginMsg").textContent = "Login failed";
  }
}

function requireAdmin() {
  if (state.role !== "ADMIN") throw new Error("Admin role required");
}

function setSelectOptions(sel, options, getValue, getLabel) {
  sel.innerHTML = "";
  for (const o of options) {
    const opt = document.createElement("option");
    opt.value = getValue(o);
    opt.textContent = getLabel(o);
    sel.appendChild(opt);
  }
}

async function adminRefreshLookups() {
  requireAdmin();

  const movies = await api("/movie/all");
  state.movies = movies;

  const auds = await api("/auditorium/all", { withAuth: true });

  setSelectOptions($("admShowMovie"), movies, (m) => m.movieID, (m) => `${m.movieID} · ${m.title}`);
  setSelectOptions($("admShowMovieForList"), movies, (m) => m.movieID, (m) => `${m.movieID} · ${m.title}`);
  setSelectOptions($("admShowAud"), auds, (a) => a.auditoriumId, (a) => `${a.auditoriumId} · ${a.name}`);
}

async function adminLoadAuditoriums() {
  requireAdmin();
  const list = await api("/auditorium/all", { withAuth: true });

  const wrap = $("admAudList");
  wrap.innerHTML = "";
  $("admAudEmpty").classList.toggle("hidden", list.length !== 0);

  for (const a of list) {
    const row = document.createElement("div");
    row.className = "card";
    row.innerHTML = `
      <div style="display:flex;align-items:center;justify-content:space-between;gap:10px;flex-wrap:wrap">
        <div>
          <div style="font-weight:650">${escapeHtml(a.name || "")}</div>
          <div class="muted">ID ${a.auditoriumId}</div>
        </div>
        <button class="link">Delete</button>
      </div>
    `;
    row.querySelector("button").onclick = async () => {
      try {
        await api(`/auditorium/${encodeURIComponent(a.auditoriumId)}`, { method: "DELETE", withAuth: true });
        toast("Auditorium deleted");
        await adminLoadAuditoriums();
        await adminRefreshLookups();
      } catch (e) {
        toast(String(e.message || e));
      }
    };
    wrap.appendChild(row);
  }
}

async function adminCreateAuditorium() {
  requireAdmin();
  $("admAudMsg").textContent = "";
  const name = $("admAudName").value.trim();
  if (!name) {
    $("admAudMsg").textContent = "Name is required";
    return;
  }
  try {
    await api("/auditorium/new", { method: "POST", withAuth: true, json: true, body: JSON.stringify({ name }) });
    $("admAudName").value = "";
    $("admAudMsg").textContent = "Created";
    await adminLoadAuditoriums();
    await adminRefreshLookups();
  } catch (e) {
    $("admAudMsg").textContent = String(e.message || e);
  }
}

async function adminCreateShow() {
  requireAdmin();
  $("admShowMsg").textContent = "";
  const movieId = Number($("admShowMovie").value);
  const auditoriumId = Number($("admShowAud").value);
  const showTime = $("admShowTime").value;
  const price = Number($("admShowPrice").value || 0);

  if (!movieId || !auditoriumId || !showTime) {
    $("admShowMsg").textContent = "Movie, auditorium, and show time are required";
    return;
  }

  try {
    await api("/show/new", {
      method: "POST",
      withAuth: true,
      json: true,
      body: JSON.stringify({ movieId, auditoriumId, showTime, price })
    });
    $("admShowMsg").textContent = "Created";
    await adminLoadShowsForSelectedMovie();
  } catch (e) {
    $("admShowMsg").textContent = String(e.message || e);
  }
}

async function adminLoadShowsForSelectedMovie() {
  requireAdmin();
  const movieId = Number($("admShowMovieForList").value);
  if (!movieId) return;
  try {
    const list = await api(`/movie/${encodeURIComponent(movieId)}/show`);
    const wrap = $("admShowList");
    wrap.innerHTML = "";
    $("admShowEmpty").classList.toggle("hidden", list.length !== 0);
    for (const s of list) {
      const row = document.createElement("div");
      row.className = "card";
      const when = s.showTime ? new Date(s.showTime).toLocaleString() : "";
      row.innerHTML = `
        <div style="display:flex;align-items:center;justify-content:space-between;gap:10px;flex-wrap:wrap">
          <div>
            <div style="font-weight:650">Show ${s.showId} · ${escapeHtml(s.auditoriumName || "")}</div>
            <div class="muted">${escapeHtml(when)} · Price ${s.price}</div>
          </div>
          <button class="link">Delete</button>
        </div>
      `;
      row.querySelector("button").onclick = async () => {
        try {
          await api(`/show/${encodeURIComponent(s.showId)}`, { method: "DELETE", withAuth: true });
          toast("Show deleted");
          await adminLoadShowsForSelectedMovie();
        } catch (e) {
          toast(String(e.message || e));
        }
      };
      wrap.appendChild(row);
    }
  } catch (e) {
    toast(String(e.message || e));
  }
}

function renderAdminMovies(list) {
  const wrap = $("admMoviesList");
  wrap.innerHTML = "";
  $("admMoviesEmpty").classList.toggle("hidden", list.length !== 0);

  for (const m of list) {
    const card = document.createElement("div");
    card.className = "card movieCard";

    const img = document.createElement("img");
    img.className = "poster";
    img.alt = m.title || "Poster";
    img.src = m.posterUrl || "";
    img.onerror = () => { img.style.display = "none"; };

    const t = document.createElement("div");
    t.className = "movieTitle";
    t.textContent = `${m.movieID} · ${m.title || "(untitled)"}`;

    const actions = document.createElement("div");
    actions.style.display = "flex";
    actions.style.gap = "10px";

    const viewBtn = document.createElement("button");
    viewBtn.textContent = "View";
    viewBtn.onclick = () => loadMovie(m.movieID);

    const delBtn = document.createElement("button");
    delBtn.textContent = "Delete";
    delBtn.onclick = async () => {
      try {
        await api(`/movie/${encodeURIComponent(m.movieID)}`, { method: "DELETE", withAuth: true });
        toast("Movie deleted");
        await adminLoadMovies();
      } catch (e) {
        toast(String(e.message || e));
      }
    };

    actions.appendChild(viewBtn);
    actions.appendChild(delBtn);

    card.appendChild(img);
    card.appendChild(t);
    card.appendChild(actions);
    wrap.appendChild(card);
  }
}

async function adminLoadMovies() {
  requireAdmin();
  const list = await api("/movie/all");
  state.movies = list;
  renderAdminMovies(list);
  await adminRefreshLookups();
}

async function adminCreateMovie() {
  requireAdmin();
  $("admMovieMsg").textContent = "";

  const title = $("admMovieTitle").value.trim();
  const duration = Number($("admMovieDuration").value);
  const ageRating = $("admMovieAge").value.trim();
  const directors = $("admMovieDirectors").value.trim();
  const casts = $("admMovieCasts").value.trim();
  const openingDay = $("admMovieOpen").value || null;
  const genresRaw = $("admMovieGenres").value || "";
  const synopsis = $("admMovieSynopsis").value || "";
  const posterFile = $("admMoviePoster").files && $("admMoviePoster").files[0];

  const genres = genresRaw
    .split(",")
    .map((x) => x.trim())
    .filter((x) => x.length);

  if (!title || !duration || !ageRating) {
    $("admMovieMsg").textContent = "Title, duration, and age rating are required";
    return;
  }
  if (!posterFile) {
    $("admMovieMsg").textContent = "Poster file is required";
    return;
  }

  const movie = {
    title,
    directors: directors || null,
    casts: casts || null,
    genres,
    openingDay,
    duration,
    ageRating,
    synopsis: synopsis || null
  };

  try {
    const fd = new FormData();
    fd.append("movie", new Blob([JSON.stringify(movie)], { type: "application/json" }));
    fd.append("poster", posterFile);
    await apiMultipart("/movie/new", fd, "POST");

    $("admMovieMsg").textContent = "Created";
    $("admMovieTitle").value = "";
    $("admMovieDuration").value = "";
    $("admMovieAge").value = "";
    $("admMovieDirectors").value = "";
    $("admMovieCasts").value = "";
    $("admMovieOpen").value = "";
    $("admMovieGenres").value = "";
    $("admMovieSynopsis").value = "";
    $("admMoviePoster").value = "";

    await adminLoadMovies();
  } catch (e) {
    $("admMovieMsg").textContent = String(e.message || e);
  }
}

async function openAdminPanel() {
  try {
    requireAdmin();
    showView("viewAdmin");
    await adminLoadAuditoriums();
    await adminLoadMovies();
    await adminLoadShowsForSelectedMovie();
  } catch (e) {
    toast(String(e.message || e));
  }
}

function init() {
  $("btnSearch").onclick = searchMovies;
  $("btnAll").onclick = loadAllMovies;
  $("q").addEventListener("keydown", (e) => { if (e.key === "Enter") searchMovies(); });

  $("backToMovies").onclick = () => showView("viewMovies");
  $("backToMovie").onclick = () => showView("viewMovie");
  $("backFromTickets").onclick = () => showView("viewMovies");
  $("backFromAdmin").onclick = () => showView("viewMovies");

  $("btnAuth").onclick = () => $("dlgAuth").showModal();
  $("btnMyTickets").onclick = loadMyTickets;
  $("btnAdmin").onclick = openAdminPanel;
  $("btnLogout").onclick = async () => {
    clearCreds();
    toast("Logged out");
    showView("viewMovies");
  };

  $("tabLogin").onclick = () => {
    $("tabLogin").classList.add("active");
    $("tabRegister").classList.remove("active");
    $("paneLogin").classList.remove("hidden");
    $("paneRegister").classList.add("hidden");
  };
  $("tabRegister").onclick = () => {
    $("tabRegister").classList.add("active");
    $("tabLogin").classList.remove("active");
    $("paneRegister").classList.remove("hidden");
    $("paneLogin").classList.add("hidden");
  };

  $("btnRegister").onclick = doRegister;
  $("btnLogin").onclick = doLogin;

  $("admAudCreate").onclick = adminCreateAuditorium;
  $("admAudRefresh").onclick = async () => {
    try { await adminLoadAuditoriums(); await adminRefreshLookups(); }
    catch (e) { toast(String(e.message || e)); }
  };

  $("admShowCreate").onclick = adminCreateShow;
  $("admShowRefresh").onclick = adminLoadShowsForSelectedMovie;
  $("admShowMovieForList").onchange = adminLoadShowsForSelectedMovie;

  $("admMovieCreate").onclick = adminCreateMovie;
  $("admMovieRefresh").onclick = adminLoadMovies;
  $("admMoviesRefresh").onclick = adminLoadMovies;

  syncAuthUI();
  refreshProfile();
  loadAllMovies();
}

document.addEventListener("DOMContentLoaded", init);
