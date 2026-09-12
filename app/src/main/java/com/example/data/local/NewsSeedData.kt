package com.example.data.local

import com.example.data.model.AdPlacement
import com.example.data.model.Category
import com.example.data.model.NewsArticle
import com.example.data.model.SiteSettings

object NewsSeedData {
    val initialCategories = listOf(
        Category(id = "cat_bd", name = "Bangladesh", slug = "bangladesh", description = "National news, socio-economic updates and regional affairs across Bangladesh", postCount = 3),
        Category(id = "cat_int", name = "International", slug = "international", description = "Global headlines, world diplomacy, conflicts, and global treaties", postCount = 3),
        Category(id = "cat_pol", name = "Politics", slug = "politics", description = "Governance, parliamentary debates, party conventions, and elections", postCount = 2),
        Category(id = "cat_biz", name = "Business", slug = "business", description = "Financial markets, central bank policy, startups, inflation, and commerce", postCount = 2),
        Category(id = "cat_tech", name = "Technology", slug = "technology", description = "Artificial intelligence, telecommunications, smartphones, cyber security", postCount = 3),
        Category(id = "cat_sport", name = "Sports", slug = "sports", description = "Cricket, football, Olympics, grand slams, and global athletic tournaments", postCount = 2),
        Category(id = "cat_ent", name = "Entertainment", slug = "entertainment", description = "Cinema, OTT releases, music industries, festivals, and cultural events", postCount = 2),
        Category(id = "cat_life", name = "Lifestyle", slug = "lifestyle", description = "Health, nutrition, urban living, travel guides, and wellbeing", postCount = 2),
        Category(id = "cat_op", name = "Opinion", slug = "opinion", description = "Editorial columns, investigative perspectives, and analytical essays", postCount = 1)
    )

    val initialAds = listOf(
        AdPlacement(
            id = "ad_header",
            name = "Header Ad",
            type = "header",
            placement = "Below Header",
            code = """<script>
  atOptions = {
    'key' : 'a90f2bd892c5e29da62af615e6ea1803',
    'format' : 'iframe',
    'height' : 250,
    'width' : 300,
    'params' : {}
  };
</script>
<script src="https://www.highrevenueformat.com/a90f2bd892a43c5e29da62af615e6ea1803/invoke.js"></script>""",
            isEnabled = true,
            updatedAt = "2026-09-11 09:00"
        ),
        AdPlacement(
            id = "ad_social_bar",
            name = "Social Bar Ad",
            type = "social_bar",
            placement = "Between Hero & Latest",
            code = """<script src="https://pl31195062.profitableratecpmnetwork.com/13/e1/f6/13e1f666062a924ae9629cc74a43c3f1.js"></script>""",
            isEnabled = true,
            updatedAt = "2026-09-11 09:00"
        ),
        AdPlacement(
            id = "ad_banner_mid",
            name = "Banner Ad",
            type = "banner",
            placement = "Between Article Lists",
            code = """<script>
  atOptions = {
    'key' : 'a90f2bd892c5e29da62af615e6ea1803',
    'format' : 'iframe',
    'height' : 250,
    'width' : 300,
    'params' : {}
  };
</script>
<script src="https://www.highrevenueformat.com/a90f2bd892a43c5e29da62af615e6ea1803/invoke.js"></script>""",
            isEnabled = true,
            updatedAt = "2026-09-11 09:00"
        ),
        AdPlacement(
            id = "ad_sidebar",
            name = "Sidebar Ad",
            type = "sidebar",
            placement = "Sidebar",
            code = """<script>
  atOptions = {
    'key' : 'a90f2bd892c5e29da62af615e6ea1803',
    'format' : 'iframe',
    'height' : 250,
    'width' : 300,
    'params' : {}
  };
</script>
<script src="https://www.highrevenueformat.com/a90f2bd892a43c5e29da62af615e6ea1803/invoke.js"></script>""",
            isEnabled = true,
            updatedAt = "2026-09-11 09:00"
        ),
        AdPlacement(
            id = "ad_article_body",
            name = "Article In-Body Ad",
            type = "article",
            placement = "Inside Article",
            code = """<script>
  atOptions = {
    'key' : 'a90f2bd892c5e29da62af615e6ea1803',
    'format' : 'iframe',
    'height' : 250,
    'width' : 300,
    'params' : {}
  };
</script>
<script src="https://www.highrevenueformat.com/a90f2bd892a43c5e29da62af615e6ea1803/invoke.js"></script>""",
            isEnabled = true,
            updatedAt = "2026-09-11 09:00"
        ),
        AdPlacement(
            id = "ad_footer",
            name = "Footer Area Ad",
            type = "footer",
            placement = "Footer Area",
            code = """<script src="https://pl31195062.profitableratecpmnetwork.com/13/e1/f6/13e1f666062a924ae9629cc74a43c3f1.js"></script>""",
            isEnabled = true,
            updatedAt = "2026-09-11 09:00"
        )
    )

    val initialSettings = SiteSettings()

    val initialArticles = listOf(
        NewsArticle(
            id = "art-hero-1",
            title = "Padma High-Speed Rail Corridor Opens New Trade Horizons Across South Asia",
            slug = "padma-high-speed-rail-corridor-opens-trade-south-asia",
            category = "Bangladesh",
            imageUrl = "https://images.unsplash.com/photo-1544620347-c4fd4a3d5957?auto=format&fit=crop&w=1200&q=80",
            excerpt = "With expanded passenger and cargo transport operations through the Padma Rail Link, economic corridors across southwest Bangladesh witness unprecedented industrial expansion.",
            content = """The commissioning of the expanded dual-gauge high-speed connectivity across the Padma multipurpose transport system marks a transformative milestone in regional logistics and cross-border connectivity.

Officials at the Ministry of Railways and transport infrastructure experts confirmed this morning that cargo transit times between the Mongla Port and central commercial hubs in Dhaka have been slashed by nearly 65 percent.

"This is not merely a bridge and a railway track; it is a catalyst that decentralizes our manufacturing base," declared senior trade analyst Dr. Farhana Rahman. Over 45 industrial parks and agro-processing hubs along Faridpur, Gopalganj, and Khulna are currently reporting accelerated capital investment from multinational logistics groups.

Furthermore, passenger travel time from the capital to the coastal districts has shrunk down to under four hours, bolstering domestic tourism and micro-commerce across southern riverine communities. Economists project that the full operational integration will contribute an incremental 1.23% to the national gross domestic product within the next fiscal cycle.

Government authorities also emphasized that safety and state-of-the-art signaling protocols are fully automated, providing zero-downtime freight reliability for perishable agricultural shipments destined for international export markets.""",
            author = "Rafiqul Islam, Senior National Correspondent",
            tags = "Bangladesh, Infrastructure, Padma Bridge, Economy, Trade",
            publishedAt = "2026-09-11 • 08:30 AM",
            updatedAt = "2026-09-11 • 08:30 AM",
            status = "PUBLISHED",
            isFeatured = true,
            isBreaking = true,
            seoTitle = "Padma High-Speed Rail Corridor Boosts South Asian Commerce | Protinews",
            seoDescription = "Padma Rail Link accelerates freight between Mongla and Dhaka, unlocking major industrial hubs.",
            readTimeMinutes = 4,
            views = 1420
        ),
        NewsArticle(
            id = "art-hero-2",
            title = "Global Climate Summit Reaches Landmark Financing Accord for Vulnerable Deltas",
            slug = "global-climate-summit-landmark-financing-accord-deltas",
            category = "International",
            imageUrl = "https://images.unsplash.com/photo-1532187863486-abf9dbad1b69?auto=format&fit=crop&w=1200&q=80",
            excerpt = "Delegates at the Geneva Climate Assembly finalized a binding $100B adaptation fund focused specifically on delta nations facing sea-level rise and riverbank erosion.",
            content = """In an overnight breakthrough negotiation, envoys from 184 member states approved the Geneva Delta Charter, committing over $100 billion in direct non-debt grants over the next five years to climate-vulnerable coastal nations.

The framework prioritizes river delta restoration, saline-resistant seed varieties, automated early warning radar networks, and cyclone-resilient community embankments.

"Developing deltas have carried an unequal burden of global greenhouse externalities," stated the summit president during the concluding press briefing. "This treaty guarantees immediate liquidity and direct technological transfers without predatory sovereign debt conditionality."

Environmental economists noted that this agreement marks the first time that loss-and-damage funds will bypass bureaucratic intermediaries through verified biometric disaster insurance payouts.""",
            author = "Elena Rostova, Geneva Bureau",
            tags = "International, Climate, Global Summit, Environment, Geneva",
            publishedAt = "2026-09-11 • 07:45 AM",
            updatedAt = "2026-09-11 • 07:45 AM",
            status = "PUBLISHED",
            isFeatured = true,
            isBreaking = true,
            seoTitle = "Geneva Climate Summit Secures $100B Delta Adaptation Accord",
            seoDescription = "Historic global accord guarantees $100B in direct climate adaptation funding for vulnerable deltas.",
            readTimeMinutes = 5,
            views = 980
        ),
        NewsArticle(
            id = "art-pol-1",
            title = "Parliamentary Committee Unveils Comprehensive Electoral Transparency Roadmap",
            slug = "parliamentary-committee-electoral-transparency-roadmap",
            category = "Politics",
            imageUrl = "https://images.unsplash.com/photo-1541872703-74c5e44368f9?auto=format&fit=crop&w=1200&q=80",
            excerpt = "New statutory provisions mandate transparent campaign expenditure audits, digital ballot oversight, and impartial observer accreditation.",
            content = """A cross-party parliamentary consensus produced a landmark legislative draft today, establishing rigorous legal frameworks for campaign financing audits and televised public debates ahead of the upcoming municipal elections.

Under the new directives, all political candidates will be required to disclose institutional campaign donations exceeding nominal thresholds within 48 hours on an open-access public ledger.

Civil society advocates and independent democratic think tanks have largely welcomed the proposal, highlighting that institutional transparency is the bedrock of civic trust and electoral legitimacy.""",
            author = "Nafis Mahmud, Political Editor",
            tags = "Politics, Governance, Parliament, Elections, Transparency",
            publishedAt = "2026-09-11 • 06:15 AM",
            updatedAt = "2026-09-11 • 06:15 AM",
            status = "PUBLISHED",
            isFeatured = false,
            isBreaking = false,
            seoTitle = "Parliamentary Roadmap for Campaign Transparency Enacted",
            seoDescription = "New electoral reforms mandate digital audit logs and open disclosure of campaign expenditures.",
            readTimeMinutes = 3,
            views = 640
        ),
        NewsArticle(
            id = "art-biz-1",
            title = "Central Bank Modernizes Fintech Liquidity Norms to Accelerate Small Enterprise Loans",
            slug = "central-bank-modernizes-fintech-liquidity-norms-sme",
            category = "Business",
            imageUrl = "https://images.unsplash.com/photo-1611974789855-9c2a0a7236a3?auto=format&fit=crop&w=1200&q=80",
            excerpt = "New digital credit scoring models will allow over 2 million cottage and small enterprises to secure collateral-free working capital via micro-merchants.",
            content = """Commercial lending institutions and licensed Mobile Financial Services (MFS) operators received regulatory clearance today to pilot automated digital micro-loans using alternative data metrics such as utility payments and QR transaction velocity.

The initiative aims to bring unbanked rural merchants into the formal economic loop, cutting loan disbursement turnaround from 14 business days down to less than three minutes.

Industry leaders expect the reform to spur over 300,000 seasonal job openings in regional handicrafts, poultry, and light engineering clusters.""",
            author = "Tanveer Ahmed, Financial Columnist",
            tags = "Business, Banking, Fintech, SME, Bangladesh Bank",
            publishedAt = "2026-09-10 • 09:10 PM",
            updatedAt = "2026-09-10 • 09:10 PM",
            status = "PUBLISHED",
            isFeatured = true,
            isBreaking = false,
            seoTitle = "Central Bank Digital Micro-Loan Guidelines for SMEs",
            seoDescription = "Micro-merchants receive instant credit scoring approvals under new central bank fintech regulations.",
            readTimeMinutes = 4,
            views = 1120
        ),
        NewsArticle(
            id = "art-tech-1",
            title = "Next-Generation Open Quantum Processors Achieve Error-Correction Breakthrough",
            slug = "next-gen-open-quantum-processors-error-correction-breakthrough",
            category = "Technology",
            imageUrl = "https://images.unsplash.com/photo-1518770660439-4636190af475?auto=format&fit=crop&w=1200&q=80",
            excerpt = "Researchers demonstrate logical qubits with error rates below 0.001%, accelerating computational simulations for new pharmaceutical drugs and clean battery cathodes.",
            content = """A consortium of international semiconductor labs and computational physicists published benchmark results today demonstrating sustained fault-tolerant quantum logic operations at room temperature.

By combining topological braiding algorithms with photonic interconnects, the team managed to execute deep molecular folding simulations that previously required supercomputers running for months.

"We have crossed the threshold from experimental novelty to industrial utility," remarked the chief research scientist. Global hardware manufacturers are already planning pilot cloud APIs for enterprise materials discovery by early next year.""",
            author = "Dr. Samantha Wright, Science & Tech Desk",
            tags = "Technology, Quantum Computing, Artificial Intelligence, Science, Innovation",
            publishedAt = "2026-09-10 • 05:40 PM",
            updatedAt = "2026-09-10 • 05:40 PM",
            status = "PUBLISHED",
            isFeatured = false,
            isBreaking = true,
            seoTitle = "Quantum Processing Breakthrough in Fault-Tolerant Computation",
            seoDescription = "Scientists achieve low-noise logical qubits, opening breakthrough possibilities in medicine and clean energy.",
            readTimeMinutes = 4,
            views = 2300
        ),
        NewsArticle(
            id = "art-sport-1",
            title = "Tigers Clinch Historic Away Series Victory with Record-Breaking Pace Attack",
            slug = "tigers-clinch-historic-away-series-victory-record-pace-attack",
            category = "Sports",
            imageUrl = "https://images.unsplash.com/photo-1540747913346-19e32dc3e97e?auto=format&fit=crop&w=1200&q=80",
            excerpt = "A blistering five-wicket spell by the young fast-bowling sensation skittles the hosts in the final session of Day 5, sealing an iconic overseas test triumph.",
            content = """In a breathtaking display of disciplined swing and seam bowling under cloudy skies, Bangladesh's national cricket squad carved their names in sporting history with an exhilarating 42-run test match victory.

Entering the final day needing six wickets with the opposition threatening to chase down the target, the fast-bowling battery maintained relentless pressure, generating vicious reverse swing to dismantle the middle order before tea.

Jubilant supporters poured into the streets of Dhaka, Chattogram, and Sylhet in impromptu celebration, as commentators around the globe hailed the emergence of a truly world-class pace vanguard.""",
            author = "Kawsar Chowdhury, Senior Sports Analyst",
            tags = "Sports, Cricket, Tigers, Test Match, Victory",
            publishedAt = "2026-09-10 • 03:20 PM",
            updatedAt = "2026-09-10 • 03:20 PM",
            status = "PUBLISHED",
            isFeatured = false,
            isBreaking = false,
            seoTitle = "Historic Away Test Series Victory for Bangladesh Tigers",
            seoDescription = "Pace attack shines as Bangladesh seals historic overseas test triumph on Day 5.",
            readTimeMinutes = 3,
            views = 3100
        ),
        NewsArticle(
            id = "art-ent-1",
            title = "Dhaka Independent Film Festival Showcases Groundbreaking Cinema from South Asia",
            slug = "dhaka-independent-film-festival-groundbreaking-cinema-south-asia",
            category = "Entertainment",
            imageUrl = "https://images.unsplash.com/photo-1489599849927-2ee91cede3ba?auto=format&fit=crop&w=1200&q=80",
            excerpt = "Over eighty feature films, boundary-pushing documentaries, and animated shorts from twelve nations premiered before sold-out auditoriums this week.",
            content = """The 14th edition of the Dhaka Independent Film Festival concluded with thunderous applause and a triumphant award gala celebrating bold storytelling and innovative digital cinematography.

The prestigious Golden Lotus was awarded to the gripping social drama 'Sultana's River', directed by debutant filmmaker Mahir Farouq, which explores the resilient lives of female boat captains navigating changing tides in the Meghna basin.

Jury president and acclaimed filmmaker noted that South Asian cinema is undergoing a creative renaissance propelled by authentic local narratives and fresh aesthetic voices.""",
            author = "Shabnam Kabir, Culture & Arts",
            tags = "Entertainment, Cinema, Film Festival, Arts, Culture",
            publishedAt = "2026-09-10 • 01:15 PM",
            updatedAt = "2026-09-10 • 01:15 PM",
            status = "PUBLISHED",
            isFeatured = false,
            isBreaking = false,
            seoTitle = "Dhaka Independent Film Festival Celebrates Cinematic Innovation",
            seoDescription = "Over eighty films screen to packed houses as South Asian filmmakers bag top festival awards.",
            readTimeMinutes = 3,
            views = 850
        ),
        NewsArticle(
            id = "art-life-1",
            title = "The Mindful City: How Urban Rooftop Micro-Gardens Are Cool Islanding Megacities",
            slug = "mindful-city-rooftop-micro-gardens-cool-megacities",
            category = "Lifestyle",
            imageUrl = "https://images.unsplash.com/photo-1530595467537-0b5996c41f2d?auto=format&fit=crop&w=1200&q=80",
            excerpt = "From Dhanmondi to Uttara, community-driven rooftop farming has reduced ambient temperatures by 3 degrees Celsius while providing fresh organic produce.",
            content = """As dense urban metropolises grapple with seasonal heat island effects, a quiet green revolution is taking root on the rooftops of thousands of high-rise apartment complexes.

By cultivating indigenous herbs, leafy greens, honeybee-friendly marigolds, and drip-irrigated fruit trees, building residents have not only slashed their peak air conditioning bills but created vibrant social spaces for intergenerational community bonding.

Urban planning experts advocate for municipal tax incentives for green-roof installations, citing immediate public health benefits and natural rainwater catchment reduction during heavy monsoon downpours.""",
            author = "Nadia Sultana, Urban Wellbeing Editor",
            tags = "Lifestyle, Rooftop Gardening, Urban Living, Health, Green Cities",
            publishedAt = "2026-09-09 • 11:30 AM",
            updatedAt = "2026-09-09 • 11:30 AM",
            status = "PUBLISHED",
            isFeatured = false,
            isBreaking = false,
            seoTitle = "How Rooftop Gardens are Transforming Urban Wellbeing",
            seoDescription = "Rooftop agriculture in megacities lowers ambient heat while reconnecting citizens with fresh organic living.",
            readTimeMinutes = 4,
            views = 720
        ),
        NewsArticle(
            id = "art-op-1",
            title = "Editorial: Why Responsible Journalism Is the Ultimate Shield in the Age of Algorithmic Noise",
            slug = "editorial-responsible-journalism-shield-age-algorithmic-noise",
            category = "Opinion",
            imageUrl = "https://images.unsplash.com/photo-1504711434969-e33886168f5c?auto=format&fit=crop&w=1200&q=80",
            excerpt = "When automated feeds reward sensationalism, the journalistic imperative must double down on verified fact-checking, field reporting, and public accountability.",
            content = """In an information landscape dominated by algorithmic echo chambers and synthetic deepfakes, the foundational covenant between a news organization and its readership has never been more vital.

At Protinews, we reaffirm our commitment to uncompromising accuracy, multi-source verification, and transparent disclosure. Algorithms can aggregate headlines, but only principled reporters on the ground can bear witness to human dignity, hold power accountable, and contextualize nuance.

Our duty is not to be first at the cost of being wrong, but to be fair, fearless, and truthful in every chronicle we publish.""",
            author = "Executive Editorial Board, Protinews",
            tags = "Opinion, Editorial, Journalism, Media Ethics, Truth",
            publishedAt = "2026-09-09 • 08:00 AM",
            updatedAt = "2026-09-09 • 08:00 AM",
            status = "PUBLISHED",
            isFeatured = false,
            isBreaking = false,
            seoTitle = "Editorial: Journalism as a Public Trust in the Digital Era",
            seoDescription = "Protinews editorial board on the enduring necessity of truth, field reporting, and ethical accountability.",
            readTimeMinutes = 4,
            views = 1590
        )
    )
}
